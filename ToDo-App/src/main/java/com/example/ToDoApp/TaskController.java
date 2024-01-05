package com.example.ToDoApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/")
    public String getAllTasks(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        model.addAttribute("newTask", new Task()); // pour le formulaire d'ajout
        return "index";
    }

    @PostMapping("/")
    public String addTask(@ModelAttribute Task newTask) {
        taskRepository.save(newTask);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            model.addAttribute("task", task);
            return "edit";
        } else {
            return "redirect:/";
        }
    }

    @PutMapping("/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task updatedTask) {
        Task existingTask = taskRepository.findById(id).orElse(null);
        if (existingTask != null) {
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setCompleted(updatedTask.isCompleted());
            taskRepository.save(existingTask);
        }
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/";
    }
}
