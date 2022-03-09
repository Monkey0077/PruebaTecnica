import React, {useState} from "react";
import { useNavigate } from "react-router-dom";
import {Button, Card, Dropdown, Modal} from "react-bootstrap";
import {List} from "./icons/List";
import {TaskController} from "../controllers/TaskController";
import { etiquetas } from "../utils/constantes";

type Props = {
    onClick?: (event: any) => void
}

type TaskProps = {
    state: number,
    serial: number,
    userID: number,
    name: string,
    description: string,
    onUpdate: (state: boolean) => void,
    type: string
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

export function CardTask({serial, state, userID, name, description, onUpdate, type = 'primary'}: TaskProps) {
    const [showModalState, setShowModalState] = useState(false)
    const [showModalDelete, setShowModalDelete] = useState(false)
    const token = sessionStorage.getItem('token') as string;
    const navigate = useNavigate();

    const handleChangeState = async (nextState: number) => {
        const response = await TaskController.updateTask({
            Description: description,
            Name: name,
            Serial: serial,
            State: nextState,
            UserID: userID,
        }, token);

        if (response.codeStatus === 200) {
            setShowModalState(false);
            onUpdate(true);
        } else if(response.status === 400){
            console.error("Ocurrio un error al enviar los datos");
        } else {
            sessionStorage.clear();
            navigate('/');
        } 
    }

    const handleDeleteState = async () => {
        const response = await TaskController.deleteTask(serial, token);
        if (response.codeStatus === 200) {
            setShowModalDelete(false);
            onUpdate(true);
        } else if(response.status === 400){
            console.error("Ocurrio un error al enviar los datos");
        } else {
            sessionStorage.clear();
            navigate('/');
        } 
    }

    return <>
        <Card className={"my-3"}>
            <Card.Header className={"border-top border-0 border-3 border-" + type}>
                <div className={"row"}>
                    <p className={"col-6 text-start mb-0"}>Tarea N° {serial}</p>

                    <p className="col-6 text-end mb-0" style={{cursor: 'pointer'}} >
                         <span onClick={() => setShowModalState(true)}>{etiquetas.btnMover} |</span>
                         <span onClick={() => setShowModalDelete(true)} className="text-danger"> {etiquetas.btnEliminar}</span>                    
                    </p>                  
                </div>
            </Card.Header>
            <Card.Body className="border-top">
                <Card.Title>{name}</Card.Title>
                <Card.Text>
                    {description}
                </Card.Text>
            </Card.Body>
        </Card>

        <Modal show={showModalState} onHide={() => setShowModalState(false)} centered>
            <Modal.Header closeButton>
                <Modal.Title>{etiquetas.lblActionsTasks}</Modal.Title>
            </Modal.Header>
            <Modal.Body className={"text-center"}>
                <p className="text-start">{etiquetas.lblMoveTask}</p>
                <Button
                    onClick={() => handleChangeState(1)}
                    className={"col-12 rounded my-2"}
                    style={{backgroundColor: '#68BBE3'}}
                    active={state !== 1}>
                    {etiquetas.lblNewTasks}
                </Button>
                <Button
                    onClick={() => handleChangeState(2)}
                    className={"col-12 rounded bg-primary my-2"}
                    active={state !== 2}>
                    {etiquetas.lblTaskProgreso}
                </Button>
                <Button
                    onClick={() => handleChangeState(3)}
                    className={"col-12 rounded bg-success my-2"}
                    active={state !== 3}>
                    {etiquetas.lblTaskTerminadas}
                </Button>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={() => setShowModalState(false)}>
                    Cancelar
                </Button>
            </Modal.Footer>
        </Modal>

        <Modal show={showModalDelete} onHide={() => setShowModalDelete(false)} centered>
            <Modal.Header closeButton>
                <Modal.Title>{etiquetas.lblDeleteTasks}</Modal.Title>
            </Modal.Header>
            <Modal.Body className={"text-center text-muted"}>
                {etiquetas.lblQuestionDeleteTask}
            </Modal.Body>
            <Modal.Footer>
                <Button variant="danger" onClick={handleDeleteState}>
                    {etiquetas.btnConfirmDelete}
                </Button>
            </Modal.Footer>
        </Modal>
    </>
}