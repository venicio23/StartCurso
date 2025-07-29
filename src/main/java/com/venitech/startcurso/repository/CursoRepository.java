package com.venitech.startcurso.repository;

import com.venitech.startcurso.model.entity.Curso;
import com.venitech.startcurso.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CursoRepository extends JpaRepository<Curso, UUID> {

    List<Curso> findByAtivoTrue();

    List<Curso> findByInstrutorandAtivoTrue(Usuario instrutor);

    @Query("SELECT c FROM Curso c WHERE c.ativo = true AND " +
           "(LOWER(c.titulo) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(c.descricao) LIKE LOWER(CONCAT('%', :termo, '%')))")
    List<Curso> buscarPorTermo(@Param("termo") String termo);
}
