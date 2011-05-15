package edgruberman.bukkit.simplegroups;

import java.util.ArrayList;
import java.util.List;

public class Group extends Member {
    
    private List<Member> members = new ArrayList<Member>();
    
    public Group(String name) {
        super(name);
    }
    
    public void load() {
        this.members = Main.getMembers(this);
    }
    
    public List<Member> getMembers() {
        return this.members;
    }
    
    public boolean isMember(String name) {
        for (Member member : members) {
            if (member.getName().equals(name)) return true;
        }
        return false;
    }
}
