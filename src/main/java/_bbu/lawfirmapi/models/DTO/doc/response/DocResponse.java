package _bbu.lawfirmapi.models.DTO.doc.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocResponse {
    private Long docId;

    private String title;

    private String fileCover;

    private String fileUrl;

    private String categoryName;



}
