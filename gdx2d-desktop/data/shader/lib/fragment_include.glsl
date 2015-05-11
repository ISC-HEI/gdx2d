#version 100

#if __VERSION__ < 130
   #define in varying
#else

#endif

#pragma debug(on)
#pragma optimize(off)

#ifdef GL_ES
	precision mediump float;
#endif


