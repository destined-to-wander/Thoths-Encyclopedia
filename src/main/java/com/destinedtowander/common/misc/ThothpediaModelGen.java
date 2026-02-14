package com.destinedtowander.common.misc;

/*
public class ThothpediaModelGen extends FabricModelProvider {
    public static final Model HALBERD_IN_HAND;

    public ThothpediaModelGen(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator generator) {
    }

    public void generateItemModels(ItemModelGenerator generator) {

        this.registerBuiltinModel(ModItems.DIAMOND_HALBERD, generator);

        for(var4 = 0; var4 < var3; ++var4) {
            if (value == dev.doctor4t.arsenal.item.AnchorbladeItem.Skin.AMBESSA) {
                this.registerTemplateWeaponInventory(HALBERD_IN_HAND, value.getName(), ArsenalItems.ANCHORBLADE, generator);
            } else {
                this.registerTemplateWeapon(HALBERD_IN_HAND, value == dev.doctor4t.arsenal.item.AnchorbladeItem.Skin.DEFAULT ? null : value.getName(), ArsenalItems.ANCHORBLADE, generator);
            }
        }

        generator.method_25733(ArsenalItems.WEAPON_RACK, class_4943.field_22938);
    }

    private static class_4942 model(String parent, @Nullable String variant, class_4945... keys) {
        return new class_4942(Optional.of(Arsenal.id(parent)), Optional.ofNullable(variant), keys);
    }

    private static class_4942 model(String parent, class_4945... keys) {
        return model(parent, (String)null, keys);
    }

    private void registerTemplateWeapon(class_4942 templateModel, @Nullable String name, class_1792 item, class_4915 generator) {
        this.registerTemplateWeaponHandheld(templateModel, name, item, generator);
        this.registerTemplateWeaponInventory(templateModel, name, item, generator);
    }

    private void registerTemplateWeapon(class_4942 templateModel, @Nullable String name, class_2960 itemId, class_4915 generator) {
        this.registerTemplateWeaponHandheld(templateModel, name, itemId, generator);
        this.registerTemplateWeaponInventory(templateModel, name, itemId, generator);
    }

    private void registerTemplateWeaponHandheld(class_4942 templateModel, @Nullable String name, class_1792 item, class_4915 generator) {
        this.registerTemplateWeaponHandheld(templateModel, name, class_7923.field_41178.method_10221(item), generator);
    }

    private void registerTemplateWeaponHandheld(class_4942 templateModel, @Nullable String name, class_2960 itemId, class_4915 generator) {
        class_2960 handheldModelName = name == null ? getItemSubId(itemId, "_in_hand") : getItemSubId(itemId, "_" + name + "_in_hand");
        class_2960 handheldTexture = name == null ? getItemId(itemId) : getItemSubId(itemId, "_" + name);
        templateModel.method_25852(handheldModelName, class_4944.method_25895(handheldTexture), generator.field_22844);
    }

    private void registerTemplateWeaponInventory(class_4942 templateModel, @Nullable String name, class_1792 item, class_4915 generator) {
        this.registerTemplateWeaponInventory(templateModel, name, class_7923.field_41178.method_10221(item), generator);
    }

    private void registerTemplateWeaponInventory(class_4942 templateModel, @Nullable String name, class_2960 itemId, class_4915 generator) {
        class_2960 inventoryTexture = name == null ? getItemSubId(itemId, "_inventory") : getItemSubId(itemId, "_" + name + "_inventory");
        this.registerTemplateWeaponInventory(templateModel, name, itemId, inventoryTexture, generator);
    }

    private void registerTemplateWeaponInventory(class_4942 templateModel, @Nullable String name, class_2960 itemModelId, class_2960 inventoryTexture, class_4915 generator) {
        class_2960 inventoryModelName = name == null ? getItemSubId(itemModelId, "_inventory") : getItemSubId(itemModelId, "_" + name + "_inventory");
        class_4943.field_22939.method_25852(inventoryModelName, class_4944.method_25895(inventoryTexture), generator.field_22844);
    }

    private void registerBuiltinModel(class_1792 item, class_4915 generator) {
        generator.field_22844.accept(class_4941.method_25840(item), new class_4940(new class_2960("builtin/entity")));
    }

    protected class_4935 variant() {
        return class_4935.method_25824();
    }

    protected <T> class_4935 variant(class_4938<T> variantSetting, T value) {
        return this.variant().method_25828(variantSetting, value);
    }

    protected <T> class_4935 variant(class_2960 model, class_4938<T> variantSetting, T value) {
        return this.model(model).method_25828(variantSetting, value);
    }

    protected class_4935 model(class_2960 model) {
        return this.variant(class_4936.field_22887, model);
    }

    protected class_4935 rotateForFace(class_4935 variant, class_2350 direction, boolean uvlock) {
        if (uvlock) {
            variant.method_25828(class_4936.field_22888, true);
        }

        switch (direction) {
            case field_11034 -> variant.method_25828(class_4936.field_22886, class_4937.field_22891);
            case field_11035 -> variant.method_25828(class_4936.field_22886, class_4937.field_22892);
            case field_11039 -> variant.method_25828(class_4936.field_22886, class_4937.field_22893);
            case field_11036 -> variant.method_25828(class_4936.field_22885, class_4937.field_22893);
            case field_11033 -> variant.method_25828(class_4936.field_22885, class_4937.field_22891);
        }

        return variant;
    }

    protected class_4935 rotateForAxis(class_4935 variant, class_2350.class_2351 axis) {
        class_4935 var10000;
        switch (axis) {
            case field_11048 -> var10000 = variant.method_25828(class_4936.field_22886, class_4937.field_22893);
            case field_11052 -> var10000 = variant.method_25828(class_4936.field_22885, class_4937.field_22891);
            case field_11051 -> var10000 = variant;
            default -> throw new IncompatibleClassChangeError();
        }

        return var10000;
    }

    public static class_2960 getItemId(class_2960 itemId) {
        return itemId.method_45138("item/");
    }

    public static class_2960 getItemSubId(class_2960 itemId, String suffix) {
        return itemId.method_45134((path) -> {
            return "item/" + path + suffix;
        });
    }

    static {
        HALBERD_IN_HAND = model("item/template_halberd_in_hand", "_in_hand", class_4945.field_23006);
    }
}*/
