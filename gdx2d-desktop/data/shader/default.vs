// A default vertex shader, adapted from the original from libgdx
#pragma debug(on)
#pragma optimize(on)

// Combined projection and view matrix, set as a constant from Java
uniform mat4 u_projTrans;

// ??
uniform sampler2D u_texture;

// Those are "in" attributes from our Java
in vec4 a_position; // The position of 1 vertex
in vec4 a_color;    // The color of 1 vertex

in vec2 a_texCoord0;	 // ??, set by libgdx
in vec2 surfacePosAttrib;// ??, set by libgdx

// Those are values produced by our fragment shader
out vec4 v_color;   // The color of each fragment, interpolated if required, passed to the fragment shader
out vec2 vTexCoord; // ?
out vec2 vSurfacePosition; // ?

void main() {
	v_color = a_color;

	vTexCoord = a_texCoord0;
	vSurfacePosition = surfacePosAttrib;

	// Project the vertex onto the plane using the transformation matrix
	gl_Position = u_projTrans * a_position;
}