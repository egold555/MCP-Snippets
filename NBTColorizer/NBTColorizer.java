package your.package.goes.here;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

/*
Note: If your using MCP, you don't need ObfuscationReflectionHelper.
		Make a getter for the variable that is passed in as a string. For example
			IN: ObfuscationReflectionHelper.getPrivateValue(NBTTagCompound.class, tag, "tagMap");
			OUT: Object o = tag.getTagMap();
			
This was ported from 1.16 to 1.12. It may have some issues still.
*/
public class NBTColorizer {

	static TextFormatting SYNTAX_HIGHLIGHTING_KEY = TextFormatting.AQUA;
	static TextFormatting SYNTAX_HIGHLIGHTING_STRING = TextFormatting.GREEN;
	static TextFormatting SYNTAX_HIGHLIGHTING_NUMBER = TextFormatting.GOLD;
	static TextFormatting SYNTAX_HIGHLIGHTING_NUMBER_TYPE = TextFormatting.RED;

	private static final Pattern SIMPLE_VALUE = Pattern.compile("[A-Za-z0-9._+-]+");

	private static ITextComponent getNameComponent(String name) {
		if (SIMPLE_VALUE.matcher(name).matches()) {
			//			return (new TextComponentString(name)).mergeStyle(SYNTAX_HIGHLIGHTING_KEY);
			return mergeStyle(new TextComponentString(name), SYNTAX_HIGHLIGHTING_KEY);
		} 
		else {
			String s = quoteAndEscape(name);
			String s1 = s.substring(0, 1);
			//ITextComponent itextcomponent = (new TextComponentString(s.substring(1, s.length() - 1))).mergeStyle(SYNTAX_HIGHLIGHTING_KEY);
			ITextComponent itextcomponent = mergeStyle((new TextComponentString(s.substring(1, s.length() - 1))), SYNTAX_HIGHLIGHTING_KEY);
			return (new TextComponentString(s1)).appendSibling(itextcomponent).appendText(s1);
		}
	}

	private static TextComponentString mergeStyle(TextComponentString in, TextFormatting tf) {
		in.getStyle().setColor(tf);
		return in;
	}

	private static String quoteAndEscape(String name) {
		StringBuilder stringbuilder = new StringBuilder(" ");
		char c0 = 0;

		for(int i = 0; i < name.length(); ++i) {
			char c1 = name.charAt(i);
			if (c1 == '\\') {
				stringbuilder.append('\\');
			} else if (c1 == '"' || c1 == '\'') {
				if (c0 == 0) {
					c0 = (char)(c1 == '"' ? 39 : 34);
				}

				if (c0 == c1) {
					stringbuilder.append('\\');
				}
			}

			stringbuilder.append(c1);
		}

		if (c0 == 0) {
			c0 = '"';
		}

		stringbuilder.setCharAt(0, c0);
		stringbuilder.append(c0);
		return stringbuilder.toString();
	}

	private static Map<String, NBTBase> getTagMap(NBTTagCompound tag) {
		Object o = ObfuscationReflectionHelper.getPrivateValue(NBTTagCompound.class, tag, "tagMap");

		if(o instanceof HashMap) {
			return (Map<String, NBTBase>)o;
		}

		return null;

	}

	public static ITextComponent toFormattedComponent(NBTBase base) {
		return toFormattedComponent(base, "", 0);
	}

	public static ITextComponent toFormattedComponent(NBTBase base, String indentation, int indentDepth) {

		if(base instanceof NBTTagCompound) {
			return toFormattedComponentCompound((NBTTagCompound) base, indentation, indentDepth);
		}
		else if(base instanceof NBTTagString) {
			return toFormattedComponentString((NBTTagString) base, indentation, indentDepth);
		}
		else if(base instanceof NBTTagInt) {
			return toFormattedComponentInteger((NBTTagInt) base, indentation, indentDepth);
		}
		else if(base instanceof NBTTagFloat) {
			return toFormattedComponentFloat((NBTTagFloat) base, indentation, indentDepth);
		}
		else if(base instanceof NBTTagShort) {
			return toFormattedComponentShort((NBTTagShort) base, indentation, indentDepth);
		}
		else if(base instanceof NBTTagLong) {
			return toFormattedComponentLong((NBTTagLong) base, indentation, indentDepth);
		}
		else if(base instanceof NBTTagByte) {
			return toFormattedComponentByte((NBTTagByte) base, indentation, indentDepth);
		}
		else if(base instanceof NBTTagDouble) {
			return toFormattedComponentDouble((NBTTagDouble) base, indentation, indentDepth);
		}
		else if(base instanceof NBTTagByteArray) {
			return toFormattedComponentByteArray((NBTTagByteArray) base, indentation, indentDepth);
		}
		else if(base instanceof NBTTagLongArray) {
			return toFormattedComponentLongArray((NBTTagLongArray) base, indentation, indentDepth);
		}
		else if(base instanceof NBTTagIntArray) {
			return toFormattedComponentIntArray((NBTTagIntArray) base, indentation, indentDepth);
		}
		else if(base instanceof NBTTagList) {
			return toFormattedComponentTagList((NBTTagList) base, indentation, indentDepth);
		}
		else if(base instanceof NBTTagEnd) {
			return new TextComponentString("");
		}

		return new TextComponentString("***ERROR:"+ base.getClass().getSimpleName() + "***");
	}

	private static ITextComponent toFormattedComponentTagList(NBTTagList tag, String indentation, int indentDepth) {
		if (tag.hasNoTags()) {
			return new TextComponentString("[]");
		} 
		//I believe this is lists of lists, not a thing in 1.12.2
//		else if (typeSet.contains(this.tagType) && this.size() <= 8) {
//			String s1 = ", ";
//			ITextComponent iformattabletextcomponent2 = new TextComponentString("[");
//
//			for(int j = 0; j < this.tagList.size(); ++j) {
//				if (j != 0) {
//					iformattabletextcomponent2.appendText(", ");
//				}
//
//				iformattabletextcomponent2.appendSibling(this.tagList.get(j).toFormattedComponent());
//			}
//
//			iformattabletextcomponent2.appendText("]");
//			return iformattabletextcomponent2;
//		} 
		else {
			ITextComponent iformattabletextcomponent = new TextComponentString("[");
			if (!indentation.isEmpty()) {
				iformattabletextcomponent.appendText("\n");
			}

			String s = String.valueOf(',');
			
			for(int i = 0; i < tag.tagCount(); ++i) {
				ITextComponent iformattabletextcomponent1 = new TextComponentString(Strings.repeat(indentation, indentDepth + 1));
				
				ITextComponent formatted = toFormattedComponent(tag.get(i), indentation, indentDepth + 1);
				
				//iformattabletextcomponent1.appendSibling(this.tagList.get(i).toFormattedComponent(indentation, indentDepth + 1));
				iformattabletextcomponent1.appendSibling(formatted);
				if (i != tag.tagCount() - 1) {
					iformattabletextcomponent1.appendText(s).appendText(indentation.isEmpty() ? " " : "\n");
				}

				iformattabletextcomponent.appendSibling(iformattabletextcomponent1);
			}

			if (!indentation.isEmpty()) {
				iformattabletextcomponent.appendText("\n").appendText(Strings.repeat(indentation, indentDepth));
			}

			iformattabletextcomponent.appendText("]");
			return iformattabletextcomponent;
		}
	}

	private static ITextComponent toFormattedComponentIntArray(NBTTagIntArray tag, String indentation, int indentDepth) {
		// ITextComponent itextcomponent = (new TextComponentString("B")).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
		ITextComponent itextcomponent = mergeStyle(new TextComponentString("I"), SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
		ITextComponent iformattabletextcomponent = (new TextComponentString("[")).appendSibling(itextcomponent).appendText(";");

		int[] arr = tag.getIntArray();

		for(int i = 0; i < arr.length; ++i) {
			//ITextComponent iformattabletextcomponent1 = (new TextComponentString(String.valueOf((int)arr[i]))).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER);
			ITextComponent iformattabletextcomponent1 = mergeStyle(new TextComponentString(String.valueOf(arr[i])), SYNTAX_HIGHLIGHTING_NUMBER);
			iformattabletextcomponent.appendText(" ").appendSibling(iformattabletextcomponent1).appendSibling(itextcomponent);
			if (i != arr.length - 1) {
				iformattabletextcomponent.appendText(",");
			}
		}

		iformattabletextcomponent.appendText("]");
		return iformattabletextcomponent;
	}

	private static ITextComponent toFormattedComponentLongArray(NBTTagLongArray tag, String indentation, int indentDepth) {
		// ITextComponent itextcomponent = (new TextComponentString("B")).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
		ITextComponent itextcomponent = mergeStyle(new TextComponentString("L"), SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
		ITextComponent iformattabletextcomponent = (new TextComponentString("[")).appendSibling(itextcomponent).appendText(";");

		//legit there is no function. Reflection time!
		long[] arr = (long[]) ObfuscationReflectionHelper.getPrivateValue(NBTTagLongArray.class, tag, "data");
//		long[] arr = tag.getLongArray();

		for(int i = 0; i < arr.length; ++i) {
			//ITextComponent iformattabletextcomponent1 = (new TextComponentString(String.valueOf((int)arr[i]))).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER);
			ITextComponent iformattabletextcomponent1 = mergeStyle(new TextComponentString(String.valueOf(arr[i])), SYNTAX_HIGHLIGHTING_NUMBER);
			iformattabletextcomponent.appendText(" ").appendSibling(iformattabletextcomponent1).appendSibling(itextcomponent);
			if (i != arr.length - 1) {
				iformattabletextcomponent.appendText(",");
			}
		}

		iformattabletextcomponent.appendText("]");
		return iformattabletextcomponent;
	}

	private static ITextComponent toFormattedComponentByteArray(NBTTagByteArray tag, String indentation, int indentDepth) {
		// ITextComponent itextcomponent = (new TextComponentString("B")).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
		ITextComponent itextcomponent = mergeStyle(new TextComponentString("B"), SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
		ITextComponent iformattabletextcomponent = (new TextComponentString("[")).appendSibling(itextcomponent).appendText(";");

		byte[] arr = tag.getByteArray();

		for(int i = 0; i < arr.length; ++i) {
			//ITextComponent iformattabletextcomponent1 = (new TextComponentString(String.valueOf((int)arr[i]))).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER);
			ITextComponent iformattabletextcomponent1 = mergeStyle(new TextComponentString(String.valueOf((int)arr[i])), SYNTAX_HIGHLIGHTING_NUMBER);
			iformattabletextcomponent.appendText(" ").appendSibling(iformattabletextcomponent1).appendSibling(itextcomponent);
			if (i != arr.length - 1) {
				iformattabletextcomponent.appendText(",");
			}
		}

		iformattabletextcomponent.appendText("]");
		return iformattabletextcomponent;
	}

	private static ITextComponent toFormattedComponentDouble(NBTTagDouble tag, String indentation, int indentDepth) {
		//return (new StringTextComponent(String.valueOf(this.data))).appendSibling(itextcomponent).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER);
		ITextComponent t1 = mergeStyle(new TextComponentString("d"), SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
		return mergeStyle(new TextComponentString(String.valueOf(tag.getDouble())), SYNTAX_HIGHLIGHTING_NUMBER).appendSibling(t1);
	}

	private static ITextComponent toFormattedComponentByte(NBTTagByte tag, String indentation, int indentDepth) {
		//return (new StringTextComponent(String.valueOf(this.data))).appendSibling(itextcomponent).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER);
		ITextComponent t1 = mergeStyle(new TextComponentString("b"), SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
		return mergeStyle(new TextComponentString(String.valueOf(tag.getByte())), SYNTAX_HIGHLIGHTING_NUMBER).appendSibling(t1);
	}

	private static ITextComponent toFormattedComponentLong(NBTTagLong tag, String indentation, int indentDepth) {
		//return (new StringTextComponent(String.valueOf(this.data))).appendSibling(itextcomponent).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER);
		ITextComponent t1 = mergeStyle(new TextComponentString("L"), SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
		return mergeStyle(new TextComponentString(String.valueOf(tag.getLong())), SYNTAX_HIGHLIGHTING_NUMBER).appendSibling(t1);
	}

	private static ITextComponent toFormattedComponentShort(NBTTagShort tag, String indentation, int indentDepth) {
		//return (new StringTextComponent(String.valueOf(this.data))).appendSibling(itextcomponent).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER);
		ITextComponent t1 = mergeStyle(new TextComponentString("s"), SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
		return mergeStyle(new TextComponentString(String.valueOf(tag.getShort())), SYNTAX_HIGHLIGHTING_NUMBER).appendSibling(t1);
	}

	private static ITextComponent toFormattedComponentFloat(NBTTagFloat tag, String indentation, int indentDepth) {
		//return (new StringTextComponent(String.valueOf(this.data))).appendSibling(itextcomponent).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER);
		ITextComponent t1 = mergeStyle(new TextComponentString("f"), SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
		return mergeStyle(new TextComponentString(String.valueOf(tag.getFloat())), SYNTAX_HIGHLIGHTING_NUMBER).appendSibling(t1);
	}

	private static ITextComponent toFormattedComponentInteger(NBTTagInt tag, String indentation, int indentDepth) {
		//return (new TextComponentString(tag.toString()).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER);
		return mergeStyle(new TextComponentString(String.valueOf(tag.getInt())), SYNTAX_HIGHLIGHTING_NUMBER);
	}

	private static ITextComponent toFormattedComponentString(NBTTagString tag, String indentation, int indentDepth) {
		String s = quoteAndEscape(tag.getString());
		String s1 = s.substring(0, 1);

		//ITextComponent itextcomponent = (new TextComponentString(s.substring(1, s.length() - 1))).mergeStyle(SYNTAX_HIGHLIGHTING_STRING);
		ITextComponent itextcomponent = mergeStyle((new TextComponentString(s.substring(1, s.length() - 1))), SYNTAX_HIGHLIGHTING_STRING);
		return (new TextComponentString(s1)).appendSibling(itextcomponent).appendText(s1);
	}

	private static ITextComponent toFormattedComponentCompound(NBTTagCompound tag, String indentation, int indentDepth) {

		Map<String, NBTBase> tagMap = getTagMap(tag);

		if(tagMap == null) {
			TextComponentString error = new TextComponentString("Error: tagMap was null");
			error.getStyle().setColor(TextFormatting.RED);
			return error;
		}

		if (tagMap.isEmpty()) {
			return new TextComponentString("{}");
		} else {
			TextComponentString iformattabletextcomponent = new TextComponentString("{");
			Collection<String> collection = tagMap.keySet();
			//			if (LOGGER.isDebugEnabled()) {
			//				List<String> list = Lists.newArrayList(tag.tagMap.keySet());
			//				Collections.sort(list);
			//				collection = list;
			//			}

			if (!indentation.isEmpty()) {
				iformattabletextcomponent.appendText("\n");
			}

			ITextComponent iformattabletextcomponent1;
			for(Iterator<String> iterator = collection.iterator(); iterator.hasNext(); iformattabletextcomponent.appendSibling(iformattabletextcomponent1)) {
				String s = iterator.next();

				//tagMap.get(s).toFormattedComponent(indentation, indentDepth + 1)
				ITextComponent recursiveFormatting = toFormattedComponent(tagMap.get(s), indentation, indentDepth + 1);

				iformattabletextcomponent1 = (new TextComponentString(Strings.repeat(indentation, indentDepth + 1))).appendSibling(getNameComponent(s)).appendText(String.valueOf(':')).appendText(" ").appendSibling(recursiveFormatting);
				if (iterator.hasNext()) {
					iformattabletextcomponent1.appendText(String.valueOf(',')).appendText(indentation.isEmpty() ? " " : "\n");
				}
			}

			if (!indentation.isEmpty()) {
				iformattabletextcomponent.appendText("\n").appendText(Strings.repeat(indentation, indentDepth));
			}

			iformattabletextcomponent.appendText("}");
			return iformattabletextcomponent;
		}
	}

}
