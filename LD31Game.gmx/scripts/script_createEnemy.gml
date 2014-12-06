///script_createEnemy(gridXIndex, gridYIndex)
var xIndex = argument0;
var yIndex = argument1;

var spawnX = xIndex*global.blockSize;
var spawnY = yIndex*global.blockSize;


// Draw emptiness where the object will spawn
if(!surface_exists(global.emptinessSurface))
{
    script_createEmptinessSurface()
}
if(!surface_exists(global.emptinessTemplateSurface))
{
    script_createEmptinessTemplateSurface();
}
surface_copy_part(global.emptinessSurface, spawnX, spawnY,
                  global.emptinessTemplateSurface, 0, 0,
                  global.blockSize, global.blockSize);
var newSprite = sprite_create_from_surface(global.emptinessSurface, 
                                           0, 0,
                                           global.backgroundWidth, global.backgroundHeight,
                                           false, false,
                                           0,0);
global.emptinessCollider.sprite_index = newSprite;


// Create the sprite for the new object
if(!surface_exists(global.desktopSurface))
{
    script_createDesktopSurface();
}
var newSprite = sprite_create_from_surface(global.desktopSurface,
                                        spawnX, spawnY,
                                        global.blockSize,global.blockSize,
                                        false, false,
                                        blockSize/2, blockSize/2);

// Actually create the new object                                        
var newInst = instance_create(spawnX + (global.blockSize/2),
                          spawnY+(global.blockSize/2),
                          obj_enemy);
newInst.sprite_index = newSprite;
newInst.mask_index = newSprite;

return newInst;
