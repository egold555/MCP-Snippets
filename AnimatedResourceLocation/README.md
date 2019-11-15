# AnimatedResourceLocation
This provided the ability to animate textures with out using Minecrafts (shitty) animated textures that don't work in many places.

## How to use
1) Split your gif up into its frames.
2) Name them 0.png through n.png -- n being the number of frames
3) Create a new AnimationResourceLocation(folder path, how many frames, frames per tick)
4) call the update function, and the render function
5) bind the texture with getTexture() 
6) Draw it on the screen
