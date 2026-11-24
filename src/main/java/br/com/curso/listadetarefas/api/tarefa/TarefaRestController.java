package br.com.curso.listadetarefas.api.tarefa;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:4200", "http://localhost:3000"}) // Já autoriza o Vite (http://localhost:5173) e demais clientes!
public class TarefaRestController {// Endpoints GET, POST, PUT e DELETE da Parte 01

    private final TarefaService tarefaService;

    public TarefaRestController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public List<Tarefa> listar() {
        return tarefaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        return tarefaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tarefa> criar(@RequestBody Tarefa tarefa) {
        Tarefa salva = tarefaService.criar(tarefa);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        return tarefaService.atualizar(id, tarefa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (tarefaService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}