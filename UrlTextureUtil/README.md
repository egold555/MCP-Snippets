# UrlTextureUtil
An adaptation to ThreadDownloadImage with a resource location callback

# How to use
```java
UrlTextureUtil.downloadAndSetTexture("https://your-url-here.com/cool-image.png", new ResourceLocationCallback() {	
	@Override
	public void onTextureLoaded(ResourceLocation rl) {
		//rl is the downloaded textur as a resource location.
	}
});
```
I have also put an example mod that Displays a fish image