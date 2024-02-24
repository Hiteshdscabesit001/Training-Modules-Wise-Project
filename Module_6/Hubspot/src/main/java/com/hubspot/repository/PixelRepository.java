package com.hubspot.repository;

import com.hubspot.entity.Pixel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PixelRepository extends JpaRepository<Pixel,Long> {
}
