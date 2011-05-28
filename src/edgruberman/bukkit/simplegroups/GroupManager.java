package edgruberman.bukkit.simplegroups;

import java.util.ArrayList;
import java.util.List;

public class GroupManager {
    
    private static List<Group> groups = new ArrayList<Group>();
    
    public static Group getGroup(String name) {
        return GroupManager.getGroup(name, false);
    }
    
    public static Group getGroup(String name, boolean reload) {
        
        // Return group from cache if possible.
        for (Group group : groups) {
            if (group.getName().equals(name)) {
                if (reload) group.load();
                return group;
            }
        }
        
        if (!Main.isGroupExists(name)) return null;
        
        Group group = new Group(name);
        group.load();
        
        groups.add(group);
        
        return group;
    }
    
}