package com.venitech.startcurso.repository;

import com.venitech.startcurso.model.entity.Inscricao;
import com.venitech.startcurso.model.entity.Pagamento;
import com.venitech.startcurso.model.entity.Usuario;
import com.venitech.startcurso.model.enums.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {

    Optional<Pagamento> findByInscricao(Inscricao inscricao);

    List<Pagamento> findByStatusPagamento(StatusPagamento status);

    @Query("SELECT p FROM Pagamento p WHERE p.inscricao.aluno = :aluno")
    List<Pagamento> findByAluno(@Param("aluno") Usuario aluno);
}
