uniform float time;
uniform vec2 resolution;

void main( void ) {	
	vec2 position = ( gl_FragCoord.xy / resolution.xy );
	vec3 col = vec3( (1.0 + sin(position.x)) / 2.0, 
					 (1.0 + sin(position.y)) / 2.0,
					 (1.0 + sin(time)) / 2.0);	
	gl_FragColor = vec4(col, 1.0 );
}
