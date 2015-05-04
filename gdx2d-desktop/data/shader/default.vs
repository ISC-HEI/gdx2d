#version 300 es
#pragma debug(on)
#pragma optimize(on)

// A default vertex shader, adapted from the original from libgdx

//combined projection and view matrix
uniform mat4 u_projTrans;

//"in" attributes from our Java
in vec4 a_position;
in vec4 a_color;

in vec2 a_texCoord0;
in vec2 surfacePosAttrib;

//"out" varyings to our fragment shader
out vec4 v_color;

out vec2 vTexCoord;
out vec2 vSurfacePosition;

void main() {
	v_color = a_color;
	vTexCoord = a_texCoord0;
	vSurfacePosition = surfacePosAttrib;
	gl_Position = u_projTrans * a_position;
}