# Change Log

## 0.0.??-alpha

- `[NEW]` Cat trees now support different wool types to make different colored trees.
- `[NEW]` Cat trees can be dyed to change their color.
- `[NEW]` Pet doors now support different wood types.
- `[FIX]` Fixed missing texture issue when putting a collar on the cat.
- `[FIX]` Add cat tree and cat toy to Item Physics.

## 0.0.75-alpha

- `[FIX]` Update recipes for cat shrinker and enlarger.
- `[DEV]` Code cleanup.

## 0.0.74-alpha

- `[FIX]` Adjust shapes and add interaction to lock/unlock the door.

## 0.0.73-alpha

- `[DEV]` Remove unused files.
- `[FIX]` Update item model for pet door.

## 0.0.72-alpha

- `[NEW]` Add model for pet door.
- `[NEW]` Add block state for pet door.
- `[FIX]` Update cardboard texture.

## 0.0.71-alpha

- `[FIX]` Change spelling of multi-colored collar to American English.
- `[FIX]` Updates to the Patchouli book.

## 0.0.70-alpha

- `[FIX]` Updated landing text.

## 0.0.69-alpha

- `[FIX]` Change the recipe for the cat bowl.

## 0.0.68-alpha

- `[FIX]` Change the ID of the Patchouli book ID, and update some of the text.

## 0.0.67-alpha

- `[DEV]` Miscellaneous cleanup.
- `[NEW]` Add pet door.
- `[NEW]` Add check that size can be changed before consuming cat sizer item.
- `[DEV]` Add mixin properties for data and server runs.

## 0.0.66-alpha

- `[NEW]` Add generated meat tag.
- `[FIX]` Update cat toy to use catnip.

## 0.0.65-alpha

- `[NEW]` Added MEAT tag, put vanilla 'meat' items in it, and added the tag to the Happy Eater talent check.

## 0.0.64-alpha

- `[FIX]` Update book data.

## 0.0.63-alpha

- `[NEW]` Add Patchouli.

## 0.0.62-alpha

- `[FIX]` Rearrange some goals.

## 0.0.61-alpha

- `[FIX]` Change priority of lie on bed goal.
- `[NEW]` Add fish food handler and remove meat food handler.

## 0.0.60-alpha

- `[FIX]` Change properties of cheese wedge to be edible.

## 0.0.59-alpha

- `[FIX]` Fix casing of litterbox classes.

## 0.0.58-alpha

- `[DEV]` Remove old mouse trap generated model file.

## 0.0.56-alpha

- `[FIX]` Update language strings.
- `[FIX]` Add description for skittish mode.
- `[FIX]` Fix placement state to use only horizontal directions on blocks that use that property type.

## 0.0.55-alpha

- `[FIX]` Restore code that assigns cat to bed.
- `[DEV]` Move mouse trap block state definition into managed resources.
- `[FIX]` Update shape handling and facing property for some blocks.

## 0.0.54-alpha

- `[DEV]` Call horizontalBlock for the mouse trap block state to handle horizontal facing variants.
- `[DEV]` Start adding function for mouse trap loot table, so it can drop cheese if armed when broken.
- `[NEW]` Add generated recipes for enlarger and shrinker.
- `[FIX]` Update language files.
- `[FIX]` Fix casing of cat bowl text.
- `[FIX]` Change some recipe ingredients.
- `[FIX]` Change name of property for armed mouse traps, for clarity.

## 0.0.53-alpha

- `[NEW]` Add cat enlarger and shrinker recipes.

## 0.0.52-alpha

- `[DEV]` Update ignore file.
- `[DEV]` Miscellaneous code cleanup.
- `[DEV]` I hate letter prefixes on variable names.
- `[FIX]` Add check that cat accepts catnip before consuming it from the player's stack.
- `[DEV]` Remove deprecation annotation on cat tree use method.
- `[FIX]` Adjust occlusion height on cat tree, so it doesn't cause adjacent blocks to disappear.
- `[FIX]` Add call to super for setting up block state definition.
- `[DEV]` Suppress some deprecation warnings.
- `[FIX]` Change strength of block so it breaks quicker.

## 0.0.51-alpha

- `[DEV]` Some code cleanup.
- `[FIX]` Replace rabbit with yarn in cat treat recipe.
- `[FIX]` Add text for cardboard item.
- `[FIX]` Consume catnip when feeding it to the cat.
- `[FIX]` Add cat bowl screen texture.
- `[FIX]` Fix data generation for mouse trap.
- `[NEW]` Add interaction code for cat tree.
- `[NEW]` Modify shape handling for some blocks.
- `[NEW]` Add facing property for some blocks.

## 0.0.50-alpha

- `[FIX]` Fix training new cats.

## 0.0.49-alpha

- `[DEV]` Update to 1.18.2

## 0.0.48-alpha

- `[DEV]` Create constants for some config strings.

## 0.0.47-alpha

- `[FIX]` Added catnip seeds to list of compostables.
- `[NEW]` Made "catnip spread" during world generation configurable.

## 0.0.46-alpha

- `[FIX]` Fix reference to our catnip item in the crate.
- `[NEW]` Toys and stuff
- `[DEV]` Update the release hash to fix changelog generation.

## 0.0.45-alpha

- `[DEV]` Formatting.
- `[DEV]` Update version of checkout action.
- `[NEW]` Add facing and placement state for cat tree.
- `[MISC]` Sync goal list with vanilla cat.
- `[FIX]` Fix where some goals get added.
- `[FIX]` Tweak cardboard box shape and placement state.
- `[NEW]` Add facing property for mouse trap.

## 0.0.44-alpha

- `[FIX]` Fix catnip generation in the wild.
- `[FIX]` Remove puppies, add kittens.
- `[FIX]` Fix bird catcher and razor-sharp claws names and constants.
- `[FIX]` Fix names of wild catnip generator.
- `[MISC]` Create FUNDING.yml
- `[MISC]` Create CODEOFCONDUCT.md

## 0.0.43-alpha

- `[DEV]` #20, #21, #22: Fixes code-scanning issue #1, #2, #3
- `[DEV]` Create SECURITY.md

## 0.0.42-alpha

- `[NEW]` Add cat sizer for lang constant updates.
- `[FIX]` #17 Update lang constants for treat items to not conflict with Doggy Talents.

## 0.0.39-alpha

- `[NEW]` Add substance to cardboard box goal.
- `[FIX]` Update use check on litterbox goal.
- `[FIX]` Change priority for littlerbox goal.
- `[NEW]` Add goals for eat/drink, using litter box, and playing in a cardboard box.
- `[GFX]` Update treat textures.
- `[DEV]` Set the parent type on the block models so that they are rotated properly in the GUI.
- `[DEV]` Create codeql-analysis.yml

## 0.0.37-alpha

- `[NEW]` Feature/cat trees
- `[FIX]` Fix cardboard box shape and positioning.
- `[DEV]` Update version of Forge.
- `[FIX]` Fix texture paths in the cat house model.
- `[NEW]` Add the cat gut model.
- `[DEV]` Miscellaneous cleanup.
- `[FIX]` Fix up the cardboard box.
- `[FIX]` Food bowl -> cat bowl

## 0.0.36-alpha

- `[NEW]` Cat trees
- `[DEV]` Miscellaneous cleanup.
- `[FIX]` Set occlusion shape on mouse trap.
- `[MISC]` Add cat gut and set loot table on vanilla cat to drop it sometimes.
- `[FIX]` Remove language entries for cat bedding.
- `[FIX]` Replace cat bed with cat tree.
- `[LANG]` Remove language constants for bedding and casing.
- `[FIX]` Fix execution chance value for catnip goal.
- `[DEV]` Remove some cruft.
- `[FIX]` Change sounds when eating cheese and breaking the cheese wheel platter.
- `[DEV]` Add constants for set block function.
- `[FIX]` Set default sprung state for placed mouse trap to false.

## 0.0.33-alpha

- `[FIX]` Add catnip language text.
- `[FIX]` Update inventory button graphics.
- `[FIX]` Fix crash on feeding cat catnip when it has no bowl.
- `[MISC]` Add some credits and a download link to README.

## 0.0.31-alpha

- `[FIX]` Also adjust position for creative mode.

## 0.0.30-alpha

- `[DEV]` Add DoggyTalents mod to dev runtime.
- `[DEV]` Cleanups.
- `[FIX]` Fix more translations.
- `[FIX]` Remove migration interaction message for pack cats.
- `[FIX]` Fix mod image path so it doesn't crash when clicking mod in mod list.
- `[FIX]` Move cat inventories button up a bit if DoggyTalents is loaded.

## 0.0.29-alpha
- `[FIX]` Miscellaneous language file and constant cleanup.
- `[NEW]` Add mouse trap.
- `[DEV]` Miscellaneous cleanup.
- `[FIX]` Fix how and where wild catnip appears.
- `[FIX]` Wild catnip now drops 0-2 seeds instead of itself.
- `[FIX]` Fix eating cheese from the cheese wheel.
- `[FIX]` Fix cat goals and some behaviors and interactions.
- `[FIX]` Fix some references to wild catnip.
- `[FIX]` Fix wild catnip tooltip text.
- `[FIX]` BedFinder now requires yarn instead of a bone.

## 0.0.28-alpha
- `[NEW]` Add treat bag recipe.
- `[DEV]` Move crop generation call.
- `[FIX]` Tinker with cheese wheel shape list (still not working properly).
- `[FIX]` Remove owner hurt goals from cat.
- `[FIX]` Fix text for wild catnip and ball of yarn.
- `[FIX]` Make yarn and cat toy throwable.
- `[DEL]` Remove throw toy language key.
- `[NEW]` Make catnip compostable.
- `[FIX]` Add images for some recipes.

## 0.0.27-alpha

- `[DEV]` Put cat toy back in.
- `[FIX]` Update catnip item texture.
- `[FIX]` Add translation key for cheese wedge.
- `[DEV]` Add generated cheese wedge model.
- `[DEV]` Update text utils translation method signature.
- `[DEV]` Cleanup, reformatting, etc.
- `[NEW]` Add catnip effect.
- `[NEW]` Added cheese wheel (almost).
- `[NEW]` Add lasagna (because reasons). >.>
- `[FIX]` Add missing cheese wedge translation.
- `[DEV]` Replace some item textures with placeholders.
- `[FIX]` Update cheese wedge item texture.
- `[FIX]` Update catnip item texture.
- `[FIX]` Update catnip bundles to actually be cat size modifier items.
- `[FIX]` Rename dire treat to wild treat.
- `[DEV]` Miscellaneous changes and renames.
- `[DEV]` Remove some unused things.
- `[FIX]` Add rodent entity (disabled), cheese, mouse trap.

## 0.0.26-alpha

- `[NEW]` Add asset for yarn.
- `[DEV]` Add 'drops-self' for catnip crop to make the data generator happy.
- `[FIX]` Fix super jump talent translation keys.

## 0.0.25-alpha

- `[DEV]` Change changelog template and type to use Markdown.

## 0.0.24-alpha

- `[NEW]` Add yarn texture.
- `[DEV]` Add de/serialization of original breed.
- `[DEV]` Fix serializer for original breed so cat shows up as the correct breed after being trained.

## 0.0.23-alpha

- `[FIX]` Fix catnip crop loot table.

## 0.0.22-alpha

- `[DEV]` Fix release hash fetching in project file.

## 0.0.21-alpha

- `[MISC]` Remove reference to pounce from notes.
- `[FIX]` Fix super jump translation key.
- `[FIX]` Update list of modes.
- `[FIX]` Fix entity data for original breed.

## 0.0.20-alpha

- `[DEV]` Remove hand-crafted US and GB translations.
- `[DEV]` Update the release hash to fix changelog generation.

## 0.0.19-alpha

- `[DEV]` Update the build to use and store the hash of the last release so the commit log is created correctly.
- `[FIX]` Store the original breed of the cat so the correct texture is selected.
- `[DEV]` Add some stub classes for new talents.
- `[FIX]` Update language files.
- `[DEV]` Convert some static strings to constants.
- `[DEV]` Fix texture paths for overrides.
- `[NEW]` Added GB translation (easy).
- `[DEV]` Fixed a syntax error.
- `[DEV]` Add generated translation files.
- `[DEV]` Move translations into the data generator.
