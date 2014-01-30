// A default vertex shader, adapted from the original from libgdx
#ifdef GL_ES
	precision mediump float;
#endif

//our attributes
attribute vec4 a_position;
attribute vec2 surfacePosAttrib;

// attribute vec4 a_color; // Not really used

//our camera matrix
uniform mat4 u_projTrans;

// varying vec4 vColor; // Not really used

varying vec2 surfacePosition;

void main()
{    
	// vColor = a_color; // Not really used
	surfacePosition = surfacePosAttrib;
	gl_Position = u_projTrans * vec4(a_position.xy, 0.0, 1.0);
}