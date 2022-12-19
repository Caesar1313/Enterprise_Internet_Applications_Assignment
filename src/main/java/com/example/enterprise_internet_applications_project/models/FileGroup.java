package com.example.enterprise_internet_applications_project.models;


import javax.persistence.*;

@Entity
@Table(name ="file_group")
public class FileGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id",nullable = false,referencedColumnName = "id")
    private Group group;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id",nullable = false,referencedColumnName = "id")
    private MyFile file;

    public FileGroup(MyFile file, Group group) {
        this.group = group;
        this.file = file;
    }

    public FileGroup() {}

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public MyFile getFile() {
        return file;
    }

    public void setFile(MyFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "FileGroup{" +
                "id=" + id +
                ", group=" + group +
                ", file=" + file +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof FileGroup){
            return this.file.getId() == ((FileGroup) obj).file.getId()
                    && this.group.getId().equals(((FileGroup) obj).group.getId());
        }
        return super.equals(obj);
    }
}
