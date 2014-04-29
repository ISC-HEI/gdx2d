// A default vertex shader, adapted from the original from libgdx
#ifdef GL_ES
	precision mediump float;
#endif

//combined projection and view matrix
uniform mat4 u_projTrans;

//"in" attributes from our Java
attribute vec4 a_position;
attribute vec2 a_texCoord0;
attribute vec4 a_color;
attribute vec2 surfacePosAttrib;

//"out" varyings to our fragment shader
varying vec4 vColor;
varying vec2 vTexCoord;
varying vec2 vSurfacePosition;

void main() {
        vColor = a_color;
        vTexCoord = a_texCoord0;
        vSurfacePosition = surfacePosAttrib;
        gl_Position = u_projTrans * a_position;
}