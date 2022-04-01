package softuni.exam.instagraphlite.models.dto;

public interface UserExportDTO {
    String getUsername();
    int getPostCount();
    String getCaption();
    double getSize();

    default String userInfo(){
        return String.format("""
                User: %s
                Post count: %d
                ==Post Details:
                ----Caption: %s
                ----Picture Size: %.2f
                """, getUsername(), getPostCount(), getCaption(), getSize());
    }

}
