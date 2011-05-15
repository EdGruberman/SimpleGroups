package edgruberman.bukkit.simplegroups;

import java.util.ArrayList;
import java.util.List;

public class GroupManager {
    
    private static List<Group> groups = new ArrayList<Group>();
    
    public static Group getGroup(String name) {
        for (Group group : groups) {
            if (group.getName().equals(name)) return group;
        }
        
        if (!Main.isGroupExists(name)) return null;
        
        Group group = new Group(name);
        group.load();
        
        groups.add(group);
        
        return group;
    }
    
}