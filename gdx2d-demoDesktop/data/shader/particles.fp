// Original code from http://glsl.heroku.com/e#13789.0
//MrOMGWTF
//I have no idea what is it
//Looks cool thou

uniform float time;
uniform sampler2D bb;
uniform vec2 resolution;
float motionblur_size = 3.0;

vec3 thing(vec2 uv, vec2 pos, vec3 color, float rad){
	return color * (1.0 / distance(uv, pos) * rad);	
}

void main( void ) {

	float time0 = time / 60.;
	float time1 = time / 50.;
	float time2 = time / 40.;
	float time3 = time / 100.;
	float time4 = time / 53.;
	float time5 = time / 74.;
	float time6 = time / 16.;
	float time7 = time / 27.;
	float time8 = time / 56.;
	float time9 = time / 72.;
	
   	vec2 p=(gl_FragCoord.xy/resolution.x)*2.0-vec2(1.0,resolution.y/resolution.x);
	p=p*4.0;
	vec3 color = vec3(0.0);
	color += thing(p, vec2(0.0), vec3(9.0, 1.0, 0.6), 0.05);
	color += thing(p, vec2(sin(time1 * 99.0), cos(time1 * 99.0)) * 1.25, vec3(8.5, 0.5, 1.0), 0.01);
	color += thing(p, vec2(-sin(time2 * 99.0), -cos(time2 * 99.0)) * 1.25, vec3(9.5, 0.5, 1.0), 0.01);

	color += thing(p, vec2(sin(time3 * 99.0 + 0.25), cos(time3 * 99.0 + 09.25)) * 0.5, vec3(6.5, .5, 1.0), 0.01);
	color += thing(p, vec2(sin(-time4 * 99.0 + 0.5), cos(time4 * 99.0+ 09.5)) * 0.3, vec3(6.5, 6.5, 1.0), 0.01);
	color += thing(p, vec2(sin(time5 * 99.0 + 0.75), cos(-time5 * 99.0+ 05.75)) * 0.6, vec3(0.5, 0.5, 1.0), 0.01);
	color += thing(p, vec2(sin(time6 * 99.0 + 1.0), cos(time6 * 99.0+ 16.0)) * 0.8, vec3(6.5, 6.5, 6.0), 0.01);
	
	color += thing(p, vec2(sin(time7 * 99.0 + 0.25), cos(time7 * 1.0 + 0.25)) * 1.5, vec3(1.0, 0.5, 0.5), 0.02);
	color += thing(p, vec2(sin(-time8 * 99.0 + 0.5), cos(-time8 * 1.0+ 7.5)) * 1.3, vec3(1.0, 0.5, 0.5), 0.02);
	color += thing(p, vec2(sin(time9 * 99.0 + 0.75), cos(-time9 * 1.0+ 4.75)) * 1.6, vec3(1.0, 0.5, 0.5), 0.02);
	color += thing(p, vec2(sin(time0 * 99.0 + 1.0), cos(time0 * 1.0+ 5.0)) * 1.8, vec3(1.0, 0.5, 0.5), 0.02);
		
	vec2 uv = gl_FragCoord.xy/resolution;
	color += texture2D(bb, vec2(uv.x, uv.y)).xyz * motionblur_size;
	color /= motionblur_size + 1.0;
	gl_FragColor = vec4( color, 1.0 );

}