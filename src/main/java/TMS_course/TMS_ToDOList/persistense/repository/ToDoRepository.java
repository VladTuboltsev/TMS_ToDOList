package TMS_course.TMS_ToDOList.persistense.repository;

import TMS_course.TMS_ToDOList.persistense.entity.ToDo;
import TMS_course.TMS_ToDOList.representation.ToDoRepr;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long> {

    @Query("select new TMS_course.TMS_ToDOList.representation.ToDoRepr(t.id, t.description, t.user.username, t.targetDate) " +
            "from ToDo t " +
            "where t.user.id = :userId")
    List<ToDoRepr> findToDosByUserId(@Param("userId") Long userId);
}
