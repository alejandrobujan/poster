package es.udc.fi.dc.fd.model.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import es.udc.fi.dc.fd.model.entities.Group;
import es.udc.fi.dc.fd.model.entities.GroupDao;
import es.udc.fi.dc.fd.model.entities.Users;
import es.udc.fi.dc.fd.model.entities.UserDao;

public class GroupService {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private UserDao user_dao;

    public Group createGroup(long owner, String name, List<Long> members) {        
        ArrayList<Users> users = new ArrayList<Users>();
        for (Long userId: members) {
        	users.add(user_dao.findById(userId).get());
        }
        Group group = new Group(owner, name, users);
        group = groupDao.save(group);
        return group;
    }

    public void addUser(Long id, Long userId) {
        Optional<Group> maybeGroup = groupDao.findById(id);
        Optional<Users> maybeUser = user_dao.findById(userId);
        maybeGroup.map(group -> {
            List<Users> currentMembers = group.getMembers();
            maybeUser.map(currentMembers::add);
            return group;
        }).ifPresent(groupDao::save);
    }
}