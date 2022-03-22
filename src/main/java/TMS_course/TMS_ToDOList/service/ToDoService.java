package TMS_course.TMS_ToDOList.service;

import TMS_course.TMS_ToDOList.persistense.entity.ToDo;
import TMS_course.TMS_ToDOList.persistense.repository.ToDoRepository;
import TMS_course.TMS_ToDOList.persistense.repository.UserRepository;
import TMS_course.TMS_ToDOList.representation.ToDoRepr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static TMS_course.TMS_ToDOList.security.Utils.getCurrentUser;


@Transactional
@Service
public class ToDoService {

    private ToDoRepository toDoRepository;

    private UserRepository userRepository;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository, UserRepository userRepository) {
        this.toDoRepository = toDoRepository;
        this.userRepository = userRepository;
    }

    public Optional<ToDoRepr> findById(Long id) {
        return toDoRepository.findById(id)
                .map(ToDoRepr::new);
    }

    public List<ToDoRepr> findToDosByUserId(Long userId) {
        return toDoRepository.findToDosByUserId(userId);
    }

    public void save(ToDoRepr toDoRepr) {
        getCurrentUser()
                .flatMap(userRepository::getUserByUsername)
                .ifPresent(user -> {
                    ToDo toDo = new ToDo();
                    toDo.setId(toDoRepr.getId());
                    toDo.setDescription(toDoRepr.getDescription());
                    toDo.setTargetDate(toDoRepr.getTargetDate());
                    toDo.setUser(user);
                    toDoRepository.save(toDo);
                });
    }

    public void delete(Long id) {
        toDoRepository.findById(id)
                .ifPresent(toDo -> toDoRepository.delete(toDo));
    }
}
