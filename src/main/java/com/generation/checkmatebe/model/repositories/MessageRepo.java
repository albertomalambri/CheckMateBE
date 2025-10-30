package com.generation.checkmatebe.model.repositories;

import com.generation.checkmatebe.model.entities.IntercomMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<IntercomMessage, Long>
{

}
