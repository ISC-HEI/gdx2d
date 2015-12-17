# gdx2d library resources

This folder contains all resources needed by the library.
These resources are shared in every projects, so please keep this folder light.

## Library resources list

1. `GdxConfig`
	* icon16.png (Windows only)
	* icon32.png
	* icon64.png

2. `GdxGraphics`
	* logo_hes.png
	* circle.png
	* RobotoSlab-Regular.ttf (default font)

3. `ShaderRenderer`
	* fragment_include.glsl
	* default.vs

4. `CircleShaderRenderer`
	* circle_aa.fp

## How to use resources

Resources needed by the library are copied to the `res/lib` folder in the final `jar` file.
To load n internal file or resource, use the following code:

```java
FileHandle fh = Gdx.files.internal("res/lib/MyResource.bin");
```
	