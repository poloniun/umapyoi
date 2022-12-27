package net.tracen.umapyoi.item;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.training.SupportContainer;
import net.tracen.umapyoi.registry.training.SupportStack;
import net.tracen.umapyoi.registry.training.SupportType;
import net.tracen.umapyoi.registry.training.TrainingSupport;

public class TrainingItem extends Item implements SupportContainer{
    private final SupportType type;
    private final Supplier<TrainingSupport> support;
    private final int level;
    public TrainingItem(SupportType type, Supplier<TrainingSupport> support, int level) {
        super(Umapyoi.defaultItemProperties());
        this.type = type;
        this.support = support;
        this.level = level;
    }
    
    protected SupportStack getSupport() {
        return Suppliers.memoize(()->new SupportStack(this.support.get(),this.level)).get();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(this.getSupport().getDescription());
    }

    @Override
    public List<SupportStack> getSupports(ItemStack stack) {
        return ImmutableList.of(this.getSupport());
    }

    @Override
    public SupportType getSupportType(ItemStack stack) {
        return this.type;
    }

    @Override
    public boolean isConsumable(ItemStack stack) {
        return true;
    }

    @Override
    public int getSupportLevel(ItemStack stack) {
        return this.level;
    }

}