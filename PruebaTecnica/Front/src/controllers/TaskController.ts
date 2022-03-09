const baseURL = process.env.REACT_APP_API
export interface TaskForm {
    Serial: number,
    Name: string,
    Description: string,
    State: number,
    UserID: number,
}

export class TaskController {
    static async createTask(task: TaskForm, token: string) {
        const response = await fetch(`${baseURL}tasks`, {
            method: "POST",
            mode: "cors",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            },
            body: JSON.stringify({
                idTarea: task.Serial,
                nombre: task.Name,
                descripcion: task.Description,
                estado: task.State,
                idUsuario: task.UserID,
            })
        })
        return await response.json();
    }

    static async readAllTask(userID: number, token: string) {
        const response = await fetch(`${baseURL}tasks?idUsuario=${userID}` , {
            method: "GET",
            mode: "cors",
            cache: 'default',
            headers: new Headers({
                'Content-Type': 'application/json',
                'Authorization': token
            })
        })
        return await response.json();
    }

    static async readById() {
        const response = await fetch(`${baseURL}tasks/1`, {
            method: "GET",
            mode: "cors"
        })
        return await response.json();
    }

    static async updateTask(task: TaskForm, token: string) {
        const response = await fetch(`${baseURL}tasks`, {
            method: "PUT",
            mode: "cors",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            },
            body: JSON.stringify({
                idTarea: task.Serial,
                nombre: task.Name,
                descripcion: task.Description,
                estado: task.State,
                idUsuario: task.UserID,
            })
        })
        return await response.json();
    }

    static async deleteTask(serial: number, token: string) {
        const response = await fetch(`${baseURL}tasks/${serial}`, {
            method: "DELETE",
            mode: "cors",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            },
        })
        return await response.json();
    }
}