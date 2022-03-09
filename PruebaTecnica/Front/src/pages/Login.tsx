import React, {useState} from "react";
import {Alert, Button, Col, Container, Form, Row} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";
import {UsuarioController} from "../controllers/UsuarioController";
import { etiquetas } from "../utils/constantes";

export function Login() {
    const [user, setUser] = useState({ Username: '', Password: '' });
    const [error, setError] = useState(false);
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const token = sessionStorage.getItem('token');
    if (token) { navigate('/tasks'); }

    const handleLogin = async () => {
        if(user.Username === '' || user.Password === '') {
            setError(true);
            setMessage(etiquetas.ErrorLogin);
            return;
        }
        const response = await UsuarioController.loginUser(user);

        if (response.codeStatus === 200) {
            // Stored the UserID in the session
            sessionStorage.setItem('User', response.data.user);
            sessionStorage.setItem('UserID', response.data.userId);
            sessionStorage.setItem('token', response.data.access_token);
            // The order is important
            navigate('/tasks');
        } else {
            setMessage(response.mensajeError);
            setError(true);
        }
    }

    const handleChange = (e: any) => {
        setError(false);
        setUser({
            ...user,
            [e.target.name]: e.target.value
        });
    };

    return <Container fluid className={"m-lg-0"}>
        <Row className={"col-lg-4 offset-lg-4 rounded shadow-lg my-lg-5"}
             style={{backgroundColor: "#DDE7FB", color: '#000'}}>
            <Col className={"my-5 m-lg-5"}>
                <h2 className={"text-center"} style={{color: ''}}>{etiquetas.lblInicioSesion}</h2>
                <p className={"text-center mt-4"}>{etiquetas.lblCredenciales}</p>
                <Form className={""}>

                    <Form.Group className={"text-start"}>
                        <Form.Label>{etiquetas.lblUsuario}</Form.Label>
                        <Form.Control
                            onChange={handleChange}
                            className="py-2"
                            type="text"
                            name="Username"
                            value={user.Username}
                            placeholder="Digite su usuario"
                            autoComplete="none"/>
                    </Form.Group>

                    <Form.Group className={"text-start mt-4"}>
                        <Form.Label>{etiquetas.lblPassword}</Form.Label>
                        <Form.Control
                            onChange={handleChange}
                            className="py-2"
                            type="password"
                            name="Password"
                            value={user.Password}
                            placeholder="Digite su contraseña"
                            autoComplete="none"
                        />
                        <Row className={"mt-3 text-end"}>
                            <Form.Check className={"ms-3 me-2 col-5 text-start"} type="checkbox" label={"Recordarme"}/>
                            <Link to={"/"} className={"col-6"} style={{textDecoration: 'none', fontWeight: 'bold'}}>{etiquetas.lblOlvidoPassword}</Link>
                        </Row>
                    </Form.Group>
                    {error && ( <Alert variant="danger" className="mt-4" > {message} </Alert>)}

                    <Button onClick={handleLogin} className={"col-12 py-2 mt-2"}> {etiquetas.btnIngresar} </Button>
                    <div className={"text-center mt-3"}>
                        <Link to={"/register"} style={{textDecoration: 'none', fontWeight: 'bold'}}>{etiquetas.btnRegistrarse}</Link>
                    </div>
                </Form>
            </Col>
        </Row>
    </Container>
}