package com.aloneCook.modules.image;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ImageRepository extends JpaRepository<Image, Long> {

	Image findByFileName(String fileName);
}
