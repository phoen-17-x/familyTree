package org.example.familyTree.dao;

import org.example.familyTree.models.Parents;
import org.example.familyTree.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Person getPerson(int id) {

        return jdbcTemplate.query("SELECT * FROM family WHERE ID = ?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);

    }

    public List<Person> getParents(int id) {

        return jdbcTemplate.query(
                "SELECT * FROM family WHERE id IN (SELECT parent_id FROM relation WHERE child_id = ?)",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class));

    }

    public List<Person> getChildren(int id) {

        return jdbcTemplate.query(
                "SELECT * FROM family WHERE id IN (SELECT child_id FROM relation WHERE parent_id = ?)",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class));

    }

    public void addParents(Parents parents) {

        Integer childID = parents.getChildID();

        Integer firstParentID = jdbcTemplate.queryForObject(
                "INSERT INTO family (name) VALUES (?) RETURNING id",
                new Object[]{parents.getParentName1()},
                Integer.class);

        Integer secondParentID = jdbcTemplate.queryForObject(
                "INSERT INTO family (name, spouse_id) VALUES (?, ?) RETURNING id",
                new Object[]{parents.getParentName2(), firstParentID},
                Integer.class);

        jdbcTemplate.update(
                "UPDATE family SET spouse_id = ? WHERE id = ?",
                secondParentID, firstParentID);

        jdbcTemplate.update(
                "INSERT INTO relation (parent_id, child_id) VALUES (?, ?), (?, ?)",
                firstParentID, childID, secondParentID, childID);

    }

    public void addSpouse(Person person) {

        Integer spouseID = jdbcTemplate.queryForObject(
                "INSERT INTO family (name, spouse_id) VALUES (?, ?) RETURNING id",
                new Object[]{person.getName(), person.getSpouse_id()},
                Integer.class);

        jdbcTemplate.update(
                "UPDATE family SET spouse_id = ? WHERE id = ?", spouseID, person.getSpouse_id());

    }

    public void addChild(Person person, Integer id) {

        Integer childID = jdbcTemplate.queryForObject(
                "INSERT INTO family (name) VALUES (?) RETURNING id",
                new Object[]{person.getName()},
                Integer.class);

        Integer spouseID = jdbcTemplate.queryForObject(
                "SELECT spouse_id FROM family WHERE id = ?",
                new Object[]{id},
                Integer.class);

        jdbcTemplate.update(
                "INSERT INTO relation (parent_id, child_id) VALUES (?, ?), (?, ?)",
                id, childID, spouseID, childID);

    }

    public void deletePerson(Integer id) {

    }
}
