package uoggmk.college.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uoggmk.college.Models.Group;
import uoggmk.college.Repositories.GroupRepository;
import uoggmk.college.Services.Exceptions.GroupAlreadyExistsException;
import uoggmk.college.Services.Exceptions.GroupNotFoundException;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public Group findByName(String name) throws GroupNotFoundException {
        Optional<Group> findedGroup = groupRepository.findByName(name);
        if(!findedGroup.isPresent()) throw new GroupNotFoundException();
        return findedGroup.get();
    }

    public Group findByGroup(Group group) throws GroupNotFoundException {
        Optional<Group> findedGroup =
                groupRepository.findByNameAndNumberAndCourse(group.getName(),group.getNumber(),group.getCourse());
        if(!findedGroup.isPresent()) throw new GroupNotFoundException();
        return findedGroup.get();
    }

    public Group findById(Long id) throws GroupNotFoundException {
        Optional<Group> findedGroup = groupRepository.findById(id);
        if(!findedGroup.isPresent()) throw new GroupNotFoundException();
        return findedGroup.get();
    }

    public void addGroup(Group group) throws GroupAlreadyExistsException {
        try{
            findByGroup(group);
            throw new GroupAlreadyExistsException();
        } catch (GroupNotFoundException e) {
            groupRepository.save(group);
        }
    }

    public List<Group> getAllGroups(){
        return groupRepository.findAll();
    }

    public Group getGroupById(Long id) throws GroupNotFoundException {
        Optional<Group> group = groupRepository.findById(id);
        if(!group.isPresent()) throw new GroupNotFoundException();
        return group.get();
    }
}
