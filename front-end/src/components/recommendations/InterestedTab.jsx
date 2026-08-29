import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { api } from "../../api/api";
import "../../pages/css/Recommendations.css";

export default function InterestedTab() {

    const navigate = useNavigate();

    const [hobbies, setHobbies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [erro, setErro] = useState("");

    useEffect(() => {
        carregarInteressados();
    }, []);

    async function carregarInteressados() {
        try {
            setLoading(true);
            setErro("");

            const userId = localStorage.getItem("userId");
            const token = localStorage.getItem("token");

            const response = await api.get(
                `/recommendation-feedbacks/${userId}?tipo=INTERESSADO`,
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            setHobbies(response.data);

        } catch (error) {
            console.error(
                "Erro ao carregar hobbies interessados:",
                error
            );

            setErro(
                "Não foi possível carregar seus hobbies de interesse."
            );
        } finally {
            setLoading(false);
        }
    }
    async function devolverParaDescobrir(hobbyId) {
                try {
                    const userId = localStorage.getItem("userId");
                    const token = localStorage.getItem("token");

                    await api.delete(
                        `/recommendation-feedbacks/${userId}/${hobbyId}`,
                        {
                            headers: {
                                Authorization: `Bearer ${token}`,
                            },
                        }
                    );

                    setHobbies((atuais) =>
                        atuais.filter(
                            (hobby) => hobby.hobbyId !== hobbyId
                        )
                    );

                } catch (error) {
                    console.error(
                        "Erro ao desfazer decisão:",
                        error
                    );

                    setErro(
                        "Não foi possível desfazer sua escolha."
                    );
                }
            }

    async function atualizarNivel(hobbyId, nivelAtual) {
        try {
            const userId = localStorage.getItem("userId");
            const token = localStorage.getItem("token");

            await api.patch(
                `/user-hobbies/${userId}/${hobbyId}/nivel`,
                {
                    nivelAtual
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            await carregarInteressados();

            setHobbies((atuais) =>
                atuais.map((hobby) =>
                    hobby.hobbyId === hobbyId
                        ? {
                            ...hobby,
                            nivelAtual: nivelAtual
                        }
                        : hobby
                )
            );

        } catch (error) {
            console.error(
                "Erro ao atualizar nível do hobby:",
                error
            );

            setErro(
                "Não foi possível atualizar seu nível."
            );
        }
    }

    async function atualizarStatus(hobbyId, statusAtual) {
        try {
            const userId = localStorage.getItem("userId");
            const token = localStorage.getItem("token");

            await api.patch(
                `/user-hobbies/${userId}/${hobbyId}/status`,
                {
                    statusAtual
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            setHobbies((atuais) =>
                atuais.map((hobby) =>
                    hobby.hobbyId === hobbyId
                        ? {
                            ...hobby,
                            statusAtual
                        }
                        : hobby
                )
            );

        } catch (error) {
            console.error(
                "Erro ao atualizar status do hobby:",
                error
            );

            setErro(
                "Não foi possível atualizar a situação do hobby."
            );
        }
    }


    if (loading) {
        return <p>Carregando hobbies...</p>;
    }

    if (erro) {
        return <p>{erro}</p>;
    }

    if (hobbies.length === 0) {
        return (
            <section>
                <h2>Tenho interesse</h2>

                <p>
                    Você ainda não marcou nenhum hobby como interessante.
                </p>
            </section>
        );
    }

    return (
        <section>
            <h2>Tenho interesse</h2>


                <div className="hobby-status-info">
                    <p>
                        <strong>ⓘ Meu nível</strong> representa quanta experiência você
                        possui com aquele hobby, mesmo que não o pratique atualmente.
                    </p>

                    <p>
                        <strong>ⓘ Situação atual</strong> representa sua relação com o
                        hobby neste momento.
                    </p>
                </div>

            <div className="recommendations-grid">
                {hobbies.map((hobby) => (
                    <div
                        key={hobby.hobbyId}
                        className="recommendation-card"
                        onClick={() =>
                            navigate(
                                `/recommendations/${hobby.hobbyId}`
                            )
                        }
                    >
                        <h3>{hobby.nome}</h3>

                        <p>{hobby.descricao}</p>

                        <p>{hobby.score} pts</p>

                        <p>
                            <strong>Categoria:</strong>{" "}
                            {hobby.categoria}
                        </p>

                        <div
                            onClick={(e) => e.stopPropagation()}
                        >
                            <label>
                                <strong>Meu nível:</strong>{" "}

                                <select
                                    value={hobby.nivelAtual ?? "INICIANTE"}
                                    onChange={(e) => {
                                        e.stopPropagation();

                                        atualizarNivel(
                                            hobby.hobbyId,
                                            e.target.value
                                        );
                                    }}
                                >
                                    <option value="INICIANTE">
                                        Iniciante
                                    </option>

                                    <option value="INTERMEDIARIO">
                                        Intermediário
                                    </option>

                                    <option value="AVANCADO">
                                        Avançado
                                    </option>
                                </select>
                            </label>
                        </div>

                        <div
                            onClick={(e) => e.stopPropagation()}
                        >
                            <label>
                                <strong>Situação atual:</strong>{" "}

                                <select
                                    value={
                                        hobby.statusAtual ?? "INTERESSADO"
                                    }
                                    onChange={(e) => {
                                        e.stopPropagation();

                                        atualizarStatus(
                                            hobby.hobbyId,
                                            e.target.value
                                        );
                                    }}
                                >
                                    <option value="INTERESSADO">
                                        Tenho interesse
                                    </option>

                                    <option value="PRATICANDO">
                                        Estou praticando
                                    </option>

                                    <option value="PAUSADO">
                                        Está pausado
                                    </option>
                                </select>
                            </label>
                        </div>

                        <details
                            className="hobby-status-help"
                            onClick={(e) => e.stopPropagation()}
                        >
                            <summary>Como funcionam essas opções?</summary>

                            <div>
                                <p>
                                    <strong>Meu nível</strong> indica sua experiência
                                    com este hobby:
                                </p>

                                <ul>
                                    <li>
                                        <strong>Iniciante:</strong> pouca ou nenhuma experiência.
                                    </li>
                                    <li>
                                        <strong>Intermediário:</strong> já possui alguma experiência.
                                    </li>
                                    <li>
                                        <strong>Avançado:</strong> possui bastante experiência.
                                    </li>
                                </ul>

                                <p>
                                    <strong>Situação atual</strong> indica sua relação
                                    com o hobby neste momento:
                                </p>

                                <ul>
                                    <li>
                                        <strong>Tenho interesse:</strong> você tem interesse
                                        em começar ou voltar ao hobby.
                                    </li>
                                    <li>
                                        <strong>Estou praticando:</strong> você pratica
                                        o hobby atualmente.
                                    </li>
                                    <li>
                                        <strong>Está pausado:</strong> você já praticou,
                                        mas não pratica atualmente.
                                    </li>
                                </ul>
                            </div>
                        </details>


                        <button
                            type="button"
                            onClick={(e) => {
                                 e.stopPropagation();

                                 devolverParaDescobrir(
                                      hobby.hobbyId
                                 );
                            }}
                        >
                            Mudei de ideia
                        </button>
                    </div>
                ))}
            </div>
        </section>
    );
}