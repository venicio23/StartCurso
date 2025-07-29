package com.venitech.startcurso.repository;

import com.venitech.startcurso.model.entity.Curso;
import com.venitech.startcurso.model.entity.Inscricao;
import com.venitech.startcurso.model.entity.Usuario;
import com.venitech.startcurso.model.enums.StatusInscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, UUID> {

    List<Inscricao> findByAluno(Usuario aluno);

    List<Inscricao> findByCurso(Curso curso);

    Optional<Inscricao> findByAlunoAndCurso(Usuario aluno, Curso curso);

    boolean existsByAlunoAndCurso(Usuario aluno, Curso curso);

    @Query("SELECT COUNT(i) FROM Inscricao i WHERE i.curso = :curso AND i.status = 'CONFIRMADA'")
    long countInscricoesConfirmadasPorCurso(@Param("curso") Curso curso);

    List<Inscricao> findByAlunoAndStatus(Usuario aluno, StatusInscricao status);
}
