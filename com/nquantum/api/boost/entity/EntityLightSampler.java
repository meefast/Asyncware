package com.nquantum.api.boost.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;

public interface EntityLightSampler<T extends Entity> {
    int bridge$getBlockLight(T entity, BlockPos pos);

    int bridge$getSkyLight(T entity, BlockPos pos);
}