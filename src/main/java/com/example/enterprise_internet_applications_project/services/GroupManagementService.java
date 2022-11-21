package com.example.enterprise_internet_applications_project.services;


import com.example.enterprise_internet_applications_project.models.Group;
import com.example.enterprise_internet_applications_project.models.Person;
import com.example.enterprise_internet_applications_project.models.PersonGroup;
import com.example.enterprise_internet_applications_project.repositories.GroupRepository;
import com.example.enterprise_internet_applications_project.repositories.PersonGroupRepository;
import com.example.enterprise_internet_applications_project.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupManagementService {

    private final PersonGroupRepository personGroupRepository;
    private final PersonRepository personRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public GroupManagementService(PersonGroupRepository personGroupRepository, PersonRepository personRepository, GroupRepository groupRepository) {
        this.personGroupRepository = personGroupRepository;
        this.personRepository = personRepository;
        this.groupRepository = groupRepository;
    }

    public void addPerson(Long personId, Long groupId) {
        Person person = personRepository.findById(personId).get();
        Group group = groupRepository.findById(groupId).get();
        PersonGroup personGroup = new PersonGroup();
        personGroup.setPerson(person);
        personGroup.setGroup(group);
        personGroupRepository.save(personGroup);
    }

    public void removePerson(Long personId,Long groupId){
        PersonGroup personGroup = personGroupRepository.findPersonInGroup(personId,groupId);
        personGroupRepository.delete(personGroup);
    }
}
