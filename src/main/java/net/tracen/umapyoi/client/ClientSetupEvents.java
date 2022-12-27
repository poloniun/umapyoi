package net.tracen.umapyoi.client;

import cn.mcmod_mmf.mmlib.client.model.BedrockModelResourceLoader;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.block.entity.BlockEntityRegistry;
import net.tracen.umapyoi.client.key.SkillKeyMapping;
import net.tracen.umapyoi.client.renderer.TrainningSuitRenderer;
import net.tracen.umapyoi.client.renderer.UmaSoulRenderer;
import net.tracen.umapyoi.client.renderer.UmaUniformRenderer;
import net.tracen.umapyoi.client.renderer.blockentity.ThreeGoddessBlockRender;
import net.tracen.umapyoi.item.ItemRegistry;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {

    @SubscribeEvent
    public static void setupClient(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            CuriosRendererRegistry.register(ItemRegistry.UMA_SOUL.get(), UmaSoulRenderer::new);
            CuriosRendererRegistry.register(ItemRegistry.TRAINNING_SUIT.get(), TrainningSuitRenderer::new);

            CuriosRendererRegistry.register(ItemRegistry.SUMMER_UNIFORM.get(),
                    UmaUniformRenderer.SummerUniformRenderer::new);
            CuriosRendererRegistry.register(ItemRegistry.WINTER_UNIFORM.get(),
                    UmaUniformRenderer.WinterUniformRenderer::new);
        });
        event.enqueueWork(()->{
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.TRAINING_FACILITY.get(), RenderType.cutoutMipped());
        });
        event.enqueueWork(() -> {
            ClientRegistry.registerKeyBinding(SkillKeyMapping.KEY_USE_SKILL);
            ClientRegistry.registerKeyBinding(SkillKeyMapping.KEY_FORMER_SKILL);
            ClientRegistry.registerKeyBinding(SkillKeyMapping.KEY_LATTER_SKILL);
        });
        
        OverlayRegistry.registerOverlayBottom("umapyoi.skill_overlay", SkillOverlay.INSTANCE);
        OverlayRegistry.registerOverlayBottom("umapyoi.motivation_overlay", MotivationOverlay.INSTANCE);
        
        BlockEntityRenderers.register(BlockEntityRegistry.THREE_GODDESS.get(), ThreeGoddessBlockRender::new);
    }

    @SubscribeEvent
    public static void resourceLoadingListener(final RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new BedrockModelResourceLoader("models/umapyoi"));
    }

}