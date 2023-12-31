package ru.example.ExchangeInfoStaticWebservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.ExchangeInfoStaticWebservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String email);

}