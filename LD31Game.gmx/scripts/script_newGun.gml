reload_delay = argument0; //Steps taken to reload
shoot_delay = argument1; //Steps taken between shots
clip_maxSize = argument2; //Shots before reloading
ammo = argument3 //Shots before gun disintegrates. A negative value will result in infinite ammo.

//Current values
reload_timing = 0;
shoot_timing = 0;
clip_curSize = clipSize_max