import React, {ReactNode, useEffect, useState} from "react";
import {Alert, Button, Col, Container, Dropdown, Form, Modal, Row} from "react-bootstrap";
import {BoxArrowRight} from "../component/icons/BoxArrowRight";
import {CardTask} from "../component/CardTask";
import {useNavigate} from "react-router-dom";
import {List} from "../component/icons/List";
import {TaskController} from "../controllers/TaskController";
import { Plus } from "../component/icons/Plus";
import { etiquetas } from "../utils/constantes";

type Props = {
    onClick?: (event: any) => void
}

// Retorna un número aleatorio entre min (incluido) y max (excluido)
function getRandomArbitrary(min: number, max: number) {
    return Math.random() * (max - min) + min;
}

// The forwardRef is important!!
// Dropdown needs access to the DOM node in order to position the Menu
const CustomToggle = React.forwardRef(({onClick}: Props, ref: any) => (
    <span
        ref={ref}
        onClick={(e) => {
            e.preventDefault();
            onClick?.(e);
        }}>
        <List size={16}/>
    </span>
));

export function Tasks() {
    const [update, setUpdate] = useState(true);

    const [name, setName] = useState('');
    const [task, setTask] = useState({nameTask: '', descriptionTask: ''});
    const [showModalCreation, setShowModalCreation] = useState(false);
    const [error, setError] = useState(false);
    const [message, setMessage] = useState('');

    const [todoTask, setTodoTask] = useState<ReactNode[]>([]);
    const [doneTask, setDoneTask] = useState<ReactNode[]>([]);
    const [progressTask, setProgressTask] = useState<ReactNode[]>([]);
    const navigate = useNavigate();

    const token = sessionStorage.getItem('token') as string;
    if (!token) { navigate('/'); }

    useEffect(() => {
        setName(sessionStorage.getItem('User') as string)
    }, []);

    useEffect(() => {
        (async () => {
            if (update) {
                const response = await TaskController.readAllTask(
                    parseInt(sessionStorage.getItem('UserID') as string), token);

                if (response.codeStatus === 200) {
                    setTodoTask(getAllToDoTask(response.listData));
                    setDoneTask(getAllDoneTask(response.listData));
                    setProgressTask(getAllProgressTask(response.listData));
                    setUpdate(false);
                } else {
                    sessionStorage.clear();
                    navigate('/');
                }
            }
        })();
    }, [update])

    const handleCreateTask = async () => {
        if (task.nameTask === '' || task.descriptionTask === '') {
            setMessage(etiquetas.lblTaskRequired);
            setError(true);
            return;
        }
        const response = await TaskController.createTask({
            Description: task.descriptionTask,
            Name: task.nameTask,
            Serial: getRandomArbitrary(5, 999999999),
            State: 1,
            UserID: parseInt(sessionStorage.getItem('UserID') as string),
        }, token);
        if (response.codeStatus === 200) {
            setUpdate(true);
            setShowModalCreation(false);
        } else if(response.status === 400){
            console.error(response);
            setMessage(etiquetas.lblTaskRequired);
            setError(true);
        } else {
            sessionStorage.clear();
            navigate('/');
        }
    }

    const handleLogOut = () => {
        sessionStorage.clear();
        navigate("/");
    }

    const handleChange = (e: any) => {
        setError(false);
        setTask({
            ...task,
            [e.target.name]: e.target.value
        });
    }

    const getAllToDoTask = (payload: Array<any>): ReactNode[] => {
        return payload.filter(item => item.estado === 1).map(item =>
            <CardTask
                key={item.idTarea}  
                serial={item.idTarea}
                state={item.estado}
                userID={parseInt(sessionStorage.getItem('UserID') as string)}
                name={item.nombre}
                type="primary"
                description={item.descripcion}
                onUpdate={setUpdate}/>);
    }

    const getAllProgressTask = (payload: Array<any>): ReactNode[] => {
        return payload.filter(item => item.estado === 2).map(item =>
            <CardTask
                key={item.idTarea}
                serial={item.idTarea}
                state={item.estado}
                userID={parseInt(sessionStorage.getItem('UserID') as string)}
                name={item.nombre}
                type="primary"
                description={item.descripcion}
                onUpdate={setUpdate}/>);
    }

    const getAllDoneTask = (payload: Array<any>): ReactNode[] => {
        return payload.filter(item => item.estado === 3).map(item =>
            <CardTask
                key={item.idTarea}
                serial={item.idTarea}
                state={item.estado}
                userID={parseInt(sessionStorage.getItem('UserID') as string)}
                name={item.nombre}
                description={item.descripcion}
                type="success"
                onUpdate={setUpdate}/>);
    }

    const openModal = (): void =>{
        setError(false);
        setMessage('');
        setShowModalCreation(true);
    }

    return <Container fluid className={"h-100 m-0 p-0"}>
        <Row className={"shadow-sm py-2"} style={{backgroundColor: "#0C2D48", color: "#fff"}}>
            <h2 className="col-8 text-start px-5">{etiquetas.lblUsuario}: {name} </h2>
            <h2 className="col-4 text-end px-5" title="Cerrar sesión" onClick={handleLogOut} style={{cursor: 'pointer'}}>
                <span style={{fontSize: '16px'}}>{etiquetas.btnLogout}<BoxArrowRight className={"ms-3 text-end"}/></span>
            </h2>            
        </Row>

        <Row className={"rounded row-cols-lg-3 row-cols-1 min-vh-100 mx-3 my-0 p-2"}>
            <Col className={"border rounded mb-3"} style={{backgroundColor: "#FFF"}}>
                <Row className="rounded p-2 text-white" style={{backgroundColor: '#68BBE3'}}>
                    <h5 className="col-10 text-start" style={{ cursor: 'pointer'}} onClick={openModal}>
                        {etiquetas.lblNewTasks}
                        <span className="mx-2"><Plus /></span>
                    </h5>
                </Row>
                {todoTask}
            </Col>

            <Col className={"border rounded mb-3"} style={{backgroundColor: "#FFF"}}>
                <Row className="bg-primary rounded text-start p-2 text-white">
                    <h5>{etiquetas.lblTaskProgreso}</h5>
                </Row>
                {progressTask}
            </Col>

            <Col className="border rounded mb-3" style={{backgroundColor: "#FFF"}}>
                <Row className="rounded text-start p-2 text-white" style={{backgroundColor: '#21B6A8'}}>
                    <h5>{etiquetas.lblTaskTerminadas}</h5>
                </Row>
                {doneTask}
            </Col>
        </Row>

        <Modal show={showModalCreation} onHide={() => setShowModalCreation(false)} centered>
            <Modal.Header closeButton>
                <Modal.Title>{etiquetas.lblRegisterTasks}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                        <Form.Label>{etiquetas.lblNameTask}:</Form.Label>
                        <Form.Control
                            onChange={handleChange}
                            type="input"
                            name="nameTask"
                            autoComplete="none"
                            aria-autocomplete={"none"}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
                        <Form.Label>{etiquetas.lblDescriptionTask}:</Form.Label>
                        <Form.Control
                            onChange={handleChange}
                            name="descriptionTask"
                            as="textarea"
                            rows={3}/>
                    </Form.Group>
                </Form>
                {error && ( <Alert variant="danger" className="mt-4" > {message} </Alert>)}
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={() => setShowModalCreation(false)}>
                    {etiquetas.btnCancelar}
                </Button>
                <Button variant="primary" onClick={handleCreateTask}>
                    {etiquetas.btnCreateTask}
                </Button>
            </Modal.Footer>
        </Modal>
    </Container>
}