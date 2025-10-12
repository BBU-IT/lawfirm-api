package _bbu.lawfirmapi.repositories;

import _bbu.lawfirmapi.models.Entity.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestRepository  extends JpaRepository<UserRequest , Long> {
}
