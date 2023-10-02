package com.github.maximuslotro.lotroExtended.mixins;

//@Mixin(LOTRBiomes.class)
//public abstract class MixinLOTRBiomes {
//	
//	@Shadow(remap=false)
//	private static PreRegisteredLOTRBiome prepare(String name, NonNullSupplier<LOTRBiomeBase> wrapperSupplier) {
//		return null;}
//
//	private static boolean modifiedShire = false;
//	@Inject(method = "prepare", at = @At("HEAD"), cancellable = true, remap=false)
//	private static void injected(String name, NonNullSupplier<LOTRBiomeBase> wrapperSupplier, CallbackInfoReturnable<PreRegisteredLOTRBiome> callback) {
//		//if(name.equals("shire")&&!modifiedShire) {
//		//	modifiedShire=true;
//		//	callback.setReturnValue(prepare("shire", () -> new DunlandBiome(true)));
//		//	return;
//		//}
//	}
//}
