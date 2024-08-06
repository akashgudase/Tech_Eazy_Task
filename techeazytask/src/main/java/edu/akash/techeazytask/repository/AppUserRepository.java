package edu.akash.techeazytask.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.akash.techeazytask.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	Optional<AppUser> findAppUserByNameAndPassword(String name, String password);

	Optional<AppUser> findAppUserByName(String name);

}
