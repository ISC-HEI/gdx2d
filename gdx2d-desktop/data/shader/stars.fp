// Star Nest by Pablo Román Andrioli
// This content is under the MIT License.
#define iterations 20
#define formuparam 0.53

#define volsteps 4// changes performance a lot
#define stepsize 0.1

#define zoom   0.800
#define tile   0.850
#define speed  0.002 

#define brightness 0.0055
#define darkmatter 0.0300
#define distfading 0.730
#define saturation 0.550

// What we get from Java, *once*
uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

/**
 * Received from vertex shader
 */
in vec4 v_color; // The interpolated color of each fragment before transform

void main(void)
{
	//get coords and direction
	vec2 uv=gl_FragCoord.xy/resolution.xy-.5;
	uv.y*=resolution.y/resolution.x;
	
	vec3 dir=vec3(uv*zoom,1.);
	float tim=time*speed+.25;

	//mouse rotation
	float a1=.5+mouse.x/resolution.x*2.;
	float a2=.8+mouse.y/resolution.y*2.;
	mat2 rot1=mat2(cos(a1),sin(a1),-sin(a1),cos(a1));
	mat2 rot2=mat2(cos(a2),sin(a2),-sin(a2),cos(a2));
	dir.xz*=rot1;
	dir.xy*=rot2;
	vec3 from=vec3(1.,.5,0.5);	
	
	// Movement of the camera
	from+=vec3(tim*2.,0,-2.);
	from.xz*=rot1;
	from.xy*=rot2;
	
	//volumetric rendering
	float s=0.1,fade=1.;
	vec3 v=vec3(0.);
	for (int r=0; r<volsteps; r++) {
		vec3 p=from+s*dir*.5;
		p = abs(vec3(tile)-mod(p,vec3(tile*2.))); // tiling fold
		float pa,a=pa=0.;
		
		for (int i=0; i<iterations; i++) { 
			p=abs(p)/dot(p,p)-formuparam; // the magic formula
			a+=abs(length(p)-pa); // absolute sum of average change
			pa=length(p);
		}
		
		float dm=max(0.,darkmatter-a*a*.001); //dark matter
		a*=a*a; // add contrast
		
		if (r>6) fade*=1.-dm; // dark matter, don't render near
		
		//v+=vec3(dm,dm*.5,0.);
		v +=fade;
		v +=vec3(s/2.0,s*s,s*s*s*s)*a*brightness*fade; // coloring based on distance
		fade *= distfading; // distance fading
		s += stepsize;
	}
	
	v=mix(vec3(length(v)),v,saturation); //color adjust
	gl_FragColor = vec4(v*.01, 1.);
}