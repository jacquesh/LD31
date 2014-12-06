///script_createEnemy(gridXIndex, gridYIndex)
var xIndex = argument0;
var yIndex = argument1;

var spawnX = xIndex*global.blockSize;
var spawnY = yIndex*global.blockSize;


// Draw emptiness where the object will spawn
var newEmptiness = instance_create(spawnX, spawnY, obj_emptinessCollider);
newEmptiness.sprite_index = global.emptinessSprite;

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
