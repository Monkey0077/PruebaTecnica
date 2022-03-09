const baseURL = process.env.REACT_APP_API

export interface UserForm {
    First: string,
    Last: string,
    Patronymic: string,
    Matronymic: string,
    Password: string,
    Username: string,
    Email: string,
}

export interface LoginForm {
    Username: string,
    Password: string,
}

// Retorna un número aleatorio entre min (incluido) y max (excluido)
function getRandomArbitrary(min: number, max: number) {
    return Math.random() * (max - min) + min;
}

export class UsuarioController {
    static async createUser(user: UserForm) {
        const response = await fetch(`${baseURL}usuario`, {
            method: "POST",
            mode: "cors",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                idUsuario: getRandomArbitrary(1, 99999999999),
                primerNombre: user.First,
                segundoNombre: user.Last,
                primerApellido: user.Patronymic,
                segundoApellido: user.Matronymic,
                correo: user.Email,
                password: user.Password,
                userName: user.Username,
                fechaCreacion: new Date(),
            })
        })
        return await response.json();
    }

    static async loginUser(login: LoginForm) {
        const response = await fetch(`${baseURL}usuario/user?grant_type=client_credentials`, {
            method: "POST",
            mode: "cors",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                userName: login.Username,
                password: login.Password,
            })
        })
        return await response.json();
    }
}