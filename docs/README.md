# ElenaBot User Guide

Welcome to **ElenaBot**, your friendly task management chatbot 🤖.  
This guide will help you get started and make the most of its features.

---

## Features

- **Add tasks** (Todo, Deadline, Event) quickly using natural commands.
- **Mark and unmark tasks** as done.
- **Delete tasks** when you no longer need them.
- **List tasks** to see what you have.
- **Find tasks** using keywords.
- **Persistent storage**: tasks are saved automatically to a file.
- **Graphical interface**: chat with ElenaBot via a GUI window.

---

## Getting Started

1. **Run the GUI**:

```bash
./gradlew run
```

## Interact with ElenaBot

- **Type commands** in the input field.
- **Press Send** or hit **Enter** to submit.

---

## Command Reference

### Adding tasks
- `todo <description>` – adds a todo task.  
- `deadline <description> /by <yyyy-MM-dd HHmm>` – adds a deadline task.  
- `event <description> /from <start> /to <end>` – adds an event task.  

### Managing tasks
- `list` – displays all tasks.  
- `mark <task number>` – marks a task as done.  
- `unmark <task number>` – marks a task as not done.  
- `delete <task number>` – removes a task.  
- `find <keyword>` – searches tasks containing the keyword.  

## Example Usage

```text
User: todo Buy groceries
ElenaBot: Got it. I've added this task:
[T][ ] Buy groceries

User: mark 1
ElenaBot: Nice! I've marked this task as done:
[T][X] Buy groceries
```

---

## Tips & Tricks

- Use exact spelling for commands to avoid errors.
- Use the `list` command often to see all tasks.
- GUI users will see a welcome message when the application starts.
- Commands are case-insensitive.

---

## Task Checklist

- [x] Add tasks (Todo, Deadline, Event)
- [x] Mark/unmark tasks
- [x] Delete tasks
- [x] List tasks
- [x] Find tasks  
