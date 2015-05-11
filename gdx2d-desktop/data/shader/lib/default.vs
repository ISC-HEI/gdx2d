// Default vertex shader for gdx2d
// mui 2015
#version 100

#pragma debug(on)
#pragma optimize(off)

#if __VERSION__ < 130
   #define varying in
#endif

#ifdef GL_ES
	precision mediump float;
#endif

// A default vertex shader, adapted from the original from libgdx
// Combined projection and view matrix, set as a constant from Java
uniform mat4 u_projTrans;
uniform vec2 resolution;

// Those are "in" attributes from our Java, set from gdx2d
in vec4 a_position; // The position of 1 vertex
in vec4 a_color;    // The color of 1 vertex
in vec4 a_normal;   // The normal of 1 vertex
in vec2 a_texCoord0;	 // Texture coordinate attribute, texture0
in vec2 surfacePosAttrib;// The current location of the vertex set by libgdx (?)

// Those are values produced by our fragment shader
out vec4 v_color;   // The color of each fragment, interpolated if required, passed to the fragment shader
out vec2 vTexCoord; // Texture 0 coordinates, transformed
out vec2 vSurfacePosition; // Vertex position, transformed

void main() {
	v_color = a_color;

	vTexCoord = a_texCoord0;
	vSurfacePosition = surfacePosAttrib;

	// Project the vertex onto the plane using the transformation matrix
	gl_Position = u_projTrans * a_position;
}