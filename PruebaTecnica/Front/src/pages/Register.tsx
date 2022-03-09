import React, {useState} from "react";
import {Alert, Button, Container, Form, Row} from "react-bootstrap";
import {UsuarioController} from "../controllers/UsuarioController";
import {Link, useNavigate} from "react-router-dom";
import { etiquetas } from "../utils/constantes";

export function Register() {
    const [error, setError] = useState(false);
    const [message, setMessage] = useState('');
    const navigate = useNavigate();
    const [user, setUser] = useState({
        Last: '',
        First: '',
        Email: '',
        Password: '',
        Username: '',
        Patronymic: '',
        Matronymic: '',
    });

    const validateForm = (): boolean => {
        if(user.First === '') { 
            setMessage(etiquetas.NameRequired);
            setError(true);
            return false;
        }
        if(user.Patronymic === '') { 
            setMessage(etiquetas.PatronymicRequired);
            setError(true);
            return false;
        }
        if(user.Email === '') { 
            setMessage(etiquetas.EmailRequired);
            setError(true);
            return false;
        }
        if(user.Password === '') { 
            setMessage(etiquetas.EmailRequired);
            setError(true);
            return false;
        }
        if(user.Username === '') { 
            setMessage(etiquetas.UserNameRequired);
            setError(true);
            return false;
        }
        return true;
    }

    const handleLogin = async () => {
        if(validateForm()) {
            const response = await UsuarioController.createUser(user);    
            if (response.codeStatus === 200) {
                sessionStorage.setItem('User', response.data.user);
                sessionStorage.setItem('UserID', response.data.userId);
                sessionStorage.setItem('token', response.data.access_token);
                navigate('/tasks');
            } else {
                setMessage(response.mensajeError);
                setError(true);
            }
        }
    }

    const handleChange = (e: any) => {
        setError(false);
        setUser({
            ...user,
            [e.target.name]: e.target.value
        });
    }

    return <Container fluid className={"m-lg-0"}>
        <Row className={"col-lg-4 offset-lg-4 rounded shadow-lg my-lg-5 py-lg-2"}
             style={{backgroundColor: "#DDE7FB", color: '#000'}}>
            <Form>
                <div className={"px-2 mx-lg-5 mt-5"}>
                    <h2 className={"text-center"} style={{color: ''}}>{etiquetas.lblRegistroUsuarios}</h2>
                    <p className={"text-center mt-4"}>{etiquetas.lblTitleRegisterUsers}</p>
                    <Form.Group className={"text-start"}>
                        <Form.Label>{etiquetas.lblNombre}:</Form.Label>
                        <Form.Control
                            onChange={handleChange}
                            name="First"
                            className="py-2"
                            type="text"
                            placeholder="Digite su nombre"
                            autoComplete="none"
                        />
                    </Form.Group>

                    <Form.Group className={"text-start"}>
                        <Form.Label>{etiquetas.lblApellido}:</Form.Label>
                        <Form.Control
                            onChange={handleChange}
                            name="Patronymic"
                            className="py-2"
                            type="text"
                            placeholder="Digite su apellido"
                            autoComplete="none"
                        />
                    </Form.Group>

                    <Form.Group className={"text-start"}>
                        <Form.Label>{etiquetas.lblEmail}:</Form.Label>
                        <Form.Control
                            onChange={handleChange}
                            name="Email"
                            className="py-2"
                            type="email"
                            placeholder="Digite su email"
                            autoComplete="none"
                        />
                    </Form.Group>

                    <Form.Group className={"text-start"}>
                        <Form.Label>{etiquetas.lblPassword}:</Form.Label>
                        <Form.Control
                            onChange={handleChange}
                            name="Password"
                            className="py-2"
                            type="password"
                            placeholder="Digite su contraseña"
                            autoComplete="none"
                        />
                    </Form.Group>

                    <Form.Group className={"text-start"}>
                        <Form.Label>{etiquetas.lblUsuario}:</Form.Label>
                        <Form.Control
                            onChange={handleChange}
                            name="Username"
                            className="py-2"
                            type="text"
                            placeholder="Digite su usuario"
                            autoComplete="none"
                        />
                    </Form.Group>

                    {error && ( <Alert variant="danger" className="mt-4" > {message} </Alert>)}

                    <Button
                        className={"col-12 mb-3 mt-4 py-2 fw-bold"}
                        onClick={handleLogin}>
                        {etiquetas.btnRegistrarse}
                    </Button>

                    <div className={"text-center mb-5"}>
                        <Link to={"/"} style={{textDecoration: 'none', fontWeight: 'bold'}}>Iniciar sesión</Link>
                    </div>
                </div>
            </Form>
        </Row>
    </Container>
}