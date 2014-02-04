#ifdef GL_ES
precision mediump float;
#endif

uniform vec2 resolution;
uniform float time;

const float Pi = 3.14159;
//eat some pi
const float pi   = 3.1415926535897932384626433832795; //pi
const float pisq = 9.8696044010893586188344909998762; //pi squared
const float picu = 31.006276680299820175476315067101; //pi cubed
const float pipi = 36.462159607207911770990826022692; //pi pied
const float sqpi = 1.7724538509055160272981674833411; //sqrt of pi
const float cupi = 1.4645918875615232630201425272638; //curt of pi
const float prpi = 1.4396194958475906883364908049738; //pirt of pi
const float twpi = 6.283185307179586476925286766559 ; //2 x pi 
const float hfpi = 1.5707963267948966192313216916398; //0.5 x pi
const float lgpi = 0.4971498726941338543512682882909; //log pi 
const float rcpi = 0.31830988618379067153776752674503;// 1/pi

const int   complexity  =7; 
const float   rez      	= pipi; 
const float speed  	= rcpi/pi; 

uniform sampler2D texture0;
uniform sampler2D texture1;

vec3 smear(vec3 xyz)
{
	
	vec2 fcn= gl_FragCoord.xy/resolution;
	const float ssiz= .005;
	vec4 xp= texture2D(texture0, fcn+vec2( ssiz,0));
	vec4 xn= texture2D(texture0, fcn+vec2(-ssiz,0));
	vec4 yp= texture2D(texture0, fcn+vec2(0, ssiz));
	vec4 yn= texture2D(texture0, fcn+vec2(0,-ssiz));
	
	vec4 xpyp= texture2D(texture0, fcn+vec2( ssiz, ssiz));
	vec4 xpyn= texture2D(texture0, fcn+vec2( ssiz,-ssiz));
	vec4 xnyp= texture2D(texture0, fcn+vec2(-ssiz, ssiz));
	vec4 xnyn= texture2D(texture0, fcn+vec2(-ssiz,-ssiz));
	
		
	//return vec3(0.,0.,0.);
	return ((vec4( ((xp+xn+yp+yn)/8. + ( xpyp+xpyn+xnyp+xnyn)/8.) ).xyz)+xyz)/2.;
}
vec3 smoot(vec3 xy) 
{
	float t= xy.z+time*speed;
	float tc = (cos(t/pisq))*twpi;	
	
	vec3 p=vec3(cos(xy.x)*sin(tc+t*sqpi),sin(xy.y*cupi)*cos(tc+t),cos(lgpi*xy.x+tc)*sin(lgpi*xy.y+tc));
	vec3 q=normalize(vec3(xy.x+cos(tc*hfpi+cupi)*rez,xy.y+sin(tc*hfpi+cupi)*rez,xy.x+xy.y));
	vec3 r=normalize(vec3(xy.x+cos(tc*hfpi)*rez*lgpi,xy.y+sin(tc*sqpi)*rez*lgpi,sin(tc+twpi)+cos(tc+twpi)));
	p = normalize((p+q+r));
	
  	for(int i=1;i<complexity+1;i++)
  	{
    		vec3 newp=p;
    		newp.x+=cos(sqrt(p.x*q.z)*pi)+sin(p.y)*(cos(float(i)*lgpi));
    		newp.y+=sin(sqrt(p.y*q.z)*pi)+cos(p.x)*(sin(float(i)*lgpi));;
    		newp.z+=sqrt((sin(p.z*p.z)+cos((q.y*q.x)+tc)+sin(p.z+float(i)/pi)));
		p = newp;
		
		q += cos(sqrt(p)*pi)+0.5;
		r += (normalize(q)/pi)+((q*float(i)/(pi*pi))*pi)+0.5;
  	}
	q = normalize((q+r));
	float d = cos((q.x+q.y+q.z)*twpi)*0.4+0.6;
	//float d = cos((length())*twpi)*0.5+0.5;
	p=(((cos(p))))*pi;
	vec3 col=(vec3((sin(p.x)*0.5+0.5)*d,((sin(p.y)*cos(p.z))*0.5+0.5)*d,(cos(p.z+p.y)*0.5+0.5)*d));
	//col = normalize(col);
	//col=vec3(d,d,d);
	//if(length(xy)>.08) col=normalize(col*(q*0.5+0.5));
	return smear(col);
	
}
void main()
{
	const float scale=0.02;
	const float scale2=200.0;
	const float timescale=0.03;
	const float timescale2=0.1;

	vec2 p=(2.0*gl_FragCoord.xy-resolution)/max(resolution.x,resolution.y);
	for(int i=0;i<50;i++)
	{
		vec2 newp=p;
		vec2 p2=p*160.0;

		float tx1=cos((time+float(i)*timescale)*3.0*timescale2)*scale2;
		float ty1=sin((time+float(i)*timescale)*3.0*timescale2)*scale2;
		float tx2=cos(-(time+float(i)*timescale)*2.0*timescale2)*scale2;
		float ty2=sin(-(time+float(i)*timescale)*2.0*timescale2)*scale2;
		float tx3=cos(-(time+float(i)*timescale)*2.0*timescale2)*scale2;
		float ty3=sin(-(time+float(i)*timescale)*2.0*timescale2)*scale2;
		newp.x+=sin((p2.y+ty1)/64.0/0.5*3.141592)*0.5*scale;
		newp.y-=cos((p2.x+tx1)/64.0/0.5*3.141592)*0.5*scale;
		newp.x+=sin((p2.y+ty2)/128.0/0.5*3.141592)*scale;
		newp.y-=cos((p2.x+tx2)/128.0/0.5*3.141592)*scale;
		newp.x+=sin((p2.y+ty3)/256.0/0.5*3.141592)*2.0*scale;
		newp.y-=cos((p2.x+tx3)/256.0/0.5*3.141592)*2.0*scale;
		p=newp;
	}
	p*=1.0;
	vec3 col=vec3(0.5*sin(3.0*p.x)+0.5,0.5*sin(3.0*p.y)+0.5,sin(p.x+p.y));
	gl_FragColor=vec4(smoot(col)*col+smear(col), 1.0);
}
