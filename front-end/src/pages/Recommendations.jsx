import { useEffect, useState } from "react";
import { useNavigate } from "react-router";

import RecommendationTabs from "../components/recommendations/RecommendationTabs";
import DiscoverTab from "../components/recommendations/DiscoverTab";
import InterestedTab from "../components/recommendations/InterestedTab";
import NotInterestedTab from "../components/recommendations/NotInterestedTab";


export default function Recommendations() {

    const navigate = useNavigate();
    const [abaAtiva, setAbaAtiva] = useState("descobrir");
    const nome = localStorage.getItem("nome");

    function logout() {
              localStorage.clear();
              navigate("/login");
          }

    return (
        <main>
            {/*ESSA É A PARTE DO CABEÇALHO QUE É COMPARTILHADO ENTRE AS 3 ABAS*/}
            <header>
                    <h1>Recomendações</h1>

                    <div>
                        <span>
                            Olá, {nome}
                        </span>

                        <button
                            type="button"
                            onClick={logout}
                        >
                            Sair
                        </button>
                    </div>
            </header>

            <RecommendationTabs
                abaAtiva={abaAtiva}
                setAbaAtiva={setAbaAtiva}
            />

            {abaAtiva === "descobrir" && (
                <DiscoverTab />
            )}

            {abaAtiva === "interessados" && (
                <InterestedTab />
            )}

            {abaAtiva === "nao-interessados" && (
                <NotInterestedTab />
            )}
        </main>
    );
}
