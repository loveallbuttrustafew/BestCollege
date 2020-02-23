package uoggmk.college.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uoggmk.college.Models.DoneLaboratory;
import uoggmk.college.Models.Laboratory;
import uoggmk.college.Models.Subject;
import uoggmk.college.Services.Exceptions.DoneLaboratoryAlreadyExistsException;
import uoggmk.college.Services.Exceptions.LaboratoryAlreadyExistsException;
import uoggmk.college.Services.Exceptions.StorageException;
import uoggmk.college.Services.Exceptions.SubjectNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {
    @Value("${upload.path}")
    private String path;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private LaboratoryService laboratoryService;

    @Autowired
    private DoneLaboratoryService doneLaboratoryService;

    public void uploadLaboratory(MultipartFile file, Long subjectId) throws StorageException, SubjectNotFoundException, LaboratoryAlreadyExistsException {
        if (file.isEmpty()) {
            throw new StorageException();
        }
        try {
            String fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();

            Subject subject = subjectService.findById(subjectId);

            Files.copy(is, Paths.get(path + subject.getGroup().getId()  + subjectId + fileName), StandardCopyOption.REPLACE_EXISTING);

            laboratoryService.addLaboratory(Laboratory.builder().originalName(fileName).subject(subject).filepath(
                    subject.getGroup().getId().toString() + subjectId.toString() + fileName).build());
        } catch (IOException e) {
            throw new StorageException();
        }
    }

    public void uploadDoneLaboratory(MultipartFile file, Long userId, Long subjectId) throws StorageException, SubjectNotFoundException, LaboratoryAlreadyExistsException {
        if (file.isEmpty()) {
            throw new StorageException();
        }
        try {
            String fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();

            Subject subject = subjectService.findById(subjectId);

            Files.copy(is, Paths.get(path + userId.toString() + subject.getGroup().getId()  + subjectId + fileName), StandardCopyOption.REPLACE_EXISTING);

            doneLaboratoryService.addDoneLaboratory(DoneLaboratory.builder().originalName(fileName).subject(subject).filepath(
                    userId.toString() + subject.getGroup().getId().toString() + subjectId.toString() + fileName).build());
        } catch (IOException e) {
            throw new StorageException();
        } catch (DoneLaboratoryAlreadyExistsException e) {
            e.printStackTrace();
        }
    }
}
